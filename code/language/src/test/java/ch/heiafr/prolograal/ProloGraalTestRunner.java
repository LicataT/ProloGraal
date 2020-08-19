package ch.heiafr.prolograal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.internal.TextListener;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import ch.heiafr.prolograal.ProloGraalTestRunner.TestCase;

public class ProloGraalTestRunner extends ParentRunner<TestCase> {

    private static final String SOURCE_SUFFIX = ".pl";
    private static final String INPUT_SUFFIX = ".input";
    private static final String OUTPUT_SUFFIX = ".output";

    private static final String LF = System.getProperty("line.separator");

    static class TestCase {
        protected final Description name;
        protected final Path path;
        protected final String sourceName;
        protected final String testInput;
        protected final String expectedOutput;
        protected String actualOutput;

        protected TestCase(Class<?> testClass, String baseName, String sourceName, Path path, String testInput, String expectedOutput) {
            this.name = Description.createTestDescription(testClass, baseName);
            this.sourceName = sourceName;
            this.path = path;
            this.testInput = testInput;
            this.expectedOutput = expectedOutput;
        }
    }

    private final List<TestCase> testCases;

    public ProloGraalTestRunner(Class<?> runningClass) throws InitializationError {
        super(runningClass);
        try {
            testCases = createTests(runningClass);
        } catch (IOException e) {
            throw new InitializationError(e);
        }
    }

    @Override
    protected Description describeChild(TestCase child) {
        return child.name;
    }

    @Override
    protected List<TestCase> getChildren() {
        return testCases;
    }

    protected static List<TestCase> createTests(final Class<?> c) throws IOException, InitializationError {
        ProloGraalTestSuite suite = c.getAnnotation(ProloGraalTestSuite.class);
        if (suite == null) {
            throw new InitializationError(String.format("@%s annotation required on class '%s' to run with '%s'.", ProloGraalTestSuite.class.getSimpleName(), c.getName(), ProloGraalTestRunner.class.getSimpleName()));
        }

        String[] paths = suite.value();

        Class<?> testCaseDirectory = c;
        if (suite.testCaseDirectory() != ProloGraalTestSuite.class) {
            testCaseDirectory = suite.testCaseDirectory();
        }
        Path root = getRootViaResourceURL(testCaseDirectory, paths);

        if (root == null) {
            for (String path : paths) {
                Path candidate = FileSystems.getDefault().getPath(path);
                if (Files.exists(candidate)) {
                    root = candidate;
                    break;
                }
            }
        }
        if (root == null && paths.length > 0) {
            throw new FileNotFoundException(paths[0]);
        }

        final Path rootPath = root;

        final List<TestCase> foundCases = new ArrayList<>();
        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path sourceFile, BasicFileAttributes attrs) throws IOException {
                String sourceName = sourceFile.getFileName().toString();
                if (sourceName.endsWith(SOURCE_SUFFIX)) {
                    String baseName = sourceName.substring(0, sourceName.length() - SOURCE_SUFFIX.length());

                    Path inputFile = sourceFile.resolveSibling(baseName + INPUT_SUFFIX);
                    String testInput = "";
                    if (Files.exists(inputFile)) {
                        testInput = readAllLines(inputFile);
                    }

                    Path outputFile = sourceFile.resolveSibling(baseName + OUTPUT_SUFFIX);
                    String expectedOutput = "";
                    if (Files.exists(outputFile)) {
                        expectedOutput = readAllLines(outputFile);
                    }

                    foundCases.add(new TestCase(c, baseName, sourceName, sourceFile, testInput, expectedOutput));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return foundCases;
    }

    /**
     * Recursively deletes a file that may represent a directory.
     */
    private static void delete(File f) {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            PrintStream err = System.err;
            err.println("Failed to delete file: " + f);
        }
    }

    /**
     * Unpacks a jar file to a temporary directory that will be removed when the VM exits.
     *
     * @param jarfilePath the path of the jar to unpack
     * @return the path of the temporary directory
     */
    private static String explodeJarToTempDir(File jarfilePath) {
        try {
            final Path jarfileDir = Files.createTempDirectory(jarfilePath.getName());
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    delete(jarfileDir.toFile());
                }
            });
            jarfileDir.toFile().deleteOnExit();
            JarFile jarfile = new JarFile(jarfilePath);
            Enumeration<JarEntry> entries = jarfile.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (!e.isDirectory()) {
                    File path = new File(jarfileDir.toFile(), e.getName().replace('/', File.separatorChar));
                    File dir = path.getParentFile();
                    dir.mkdirs();
                    assert dir.exists();
                    Files.copy(jarfile.getInputStream(e), path.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
            jarfile.close();
            return jarfileDir.toFile().getAbsolutePath();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static Path getRootViaResourceURL(final Class<?> c, String[] paths) {
        URL url = c.getResource(c.getSimpleName() + ".class");
        if (url != null) {
            char sep = File.separatorChar;
            String externalForm = url.toExternalForm();
            String classPart = sep + c.getName().replace('.', sep) + ".class";
            String prefix = null;
            String base;
            if (externalForm.startsWith("jar:file:")) {
                prefix = "jar:file:";
                int bang = externalForm.indexOf('!', prefix.length());
                Assume.assumeTrue(bang != -1);
                File jarfilePath = new File(externalForm.substring(prefix.length(), bang));
                Assume.assumeTrue(jarfilePath.exists());
                base = explodeJarToTempDir(jarfilePath);
            } else if (externalForm.startsWith("file:")) {
                prefix = "file:";
                base = externalForm.substring(prefix.length(), externalForm.length() - classPart.length());
            } else {
                return null;
            }
            for (String path : paths) {
                String candidate = base + sep + path;
                if (new File(candidate).exists()) {
                    return FileSystems.getDefault().getPath(candidate);
                }
            }
        }
        return null;
    }

    private static String readAllLines(Path file) throws IOException {
        // fix line feeds for non unix os
        StringBuilder outFile = new StringBuilder();
        for (String line : Files.readAllLines(file, Charset.defaultCharset())) {
            outFile.append(line).append(LF);
        }
        return outFile.toString();
    }

   private String buildErrorMsg(String title, String input, List<String> expectedLines, List<String> actualLines) {
      return title +
              "\nInput : \n" +
              input +
              "\nExpected : \n" +
              expectedLines.stream().map(x -> "\t" + x).collect(Collectors.joining("\n")) +
              "\nActual : \n" +
              actualLines.stream().map(x -> "\t" + x).collect(Collectors.joining("\n")) +
              "\n";
   }

   private void addError(List<String> errors, String title, String input, List<String> expectedLines, List<String> actualLines) {
      String errorMsg = buildErrorMsg(
              title,
              input,
              expectedLines,
              actualLines);
      System.err.println(errorMsg);
      errors.add(errorMsg);
   }

    @Override
    protected void runChild(TestCase testCase, RunNotifier notifier) {
        notifier.fireTestStarted(testCase.name);

        Context context = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            context = Context.newBuilder().in(new ByteArrayInputStream(testCase.testInput.getBytes(StandardCharsets.UTF_8))).out(out).build();
            PrintWriter printer = new PrintWriter(out);
            run(context, testCase.path, printer);
            printer.flush();

            String actualOutput = new String(out.toByteArray());

            String[] input = testCase.testInput.split("\\R");

            List<List<String>> linesPerInput;
            linesPerInput =
                    Arrays.stream(actualOutput.split("\\?- "))
                            .filter(s -> !s.isEmpty())
                            .map(s -> s.split("\\R"))
                            .map(Arrays::asList)
                            .map(x -> x.stream()
                                    .filter(s -> !s.isEmpty())
                                    .collect(Collectors.toList()))
                            .collect(Collectors.toList());

            List<List<String>> expectedLinesForInput;

            expectedLinesForInput =
                    Arrays.stream(testCase.expectedOutput.split("%.*\\R"))
                            .skip(1)
                            .map(x -> x.split("\\R"))
                            .map(Arrays::asList)
                            .collect(Collectors.toList());

            System.out.println("-----" + testCase.name + "-----");
            System.out.println();
            System.out.println();
            System.out.println("----- EXPECTED -----");
            System.out.println(expectedLinesForInput);
            System.out.println();
            System.out.println("-----  ACTUAL  -----");
            System.out.println(linesPerInput);
            System.out.println();
            System.out.println();

            Assert.assertEquals(input.length, linesPerInput.size()-1); // -1 for the "yes" produced while exiting
            Assert.assertEquals(linesPerInput.size(), expectedLinesForInput.size());

            List<String> errors = new ArrayList<>();

            outer: for (int i = 0; i < linesPerInput.size(); i++) {
                List<String> actualLines = linesPerInput.get(i);
                List<String> expectedLines = expectedLinesForInput.get(i);

                String msg;

                if(i < linesPerInput.size()-1) {
                    msg = input[i];
                } else {
                    msg = "EOF";
                }

                if(expectedLines.size() != actualLines.size()) {
                    addError(errors,
                            "Expected number of lines is different from actual",
                            msg,
                            expectedLines,
                            actualLines);
                    continue;
                }
                for(int j = 0; j < actualLines.size(); j++) {
                   if (!expectedLines.get(j).equals(actualLines.get(j))) {
                      addError(
                              errors,
                              "Line content is different from what was expected",
                              msg,
                              expectedLines,
                              actualLines
                      );
                      continue outer;
                   }
                }
            }
           if (!errors.isEmpty()) {
              throw new AssertionError(errors.get(0));
           }

        } catch (Throwable ex) {
            notifier.fireTestFailure(new Failure(testCase.name, ex));
        } finally {
            if (context != null) {
                context.close();
            }
            notifier.fireTestFinished(testCase.name);
        }
    }

   private static void run(Context context, Path path, PrintWriter out) throws IOException {
        try {
            /* Parse the SL source file. */
            Source source = Source.newBuilder(ProloGraalLanguage.ID, path.toFile()).interactive(true).build();

            /* Call the main entry point, without any arguments. */
            context.eval(source);
        } catch (PolyglotException ex) {
            if (!ex.isInternalError()) {
                out.println(ex.getMessage());
            } else {
                throw ex;
            }
        }
    }

    public static void runInMain(Class<?> testClass, String[] args) throws InitializationError, NoTestsRemainException {
        JUnitCore core = new JUnitCore();
        core.addListener(new TextListener(System.out));
        ProloGraalTestRunner suite = new ProloGraalTestRunner(testClass);
        if (args.length > 0) {
            suite.filter(new NameFilter(args[0]));
        }
        Result r = core.run(suite);
        if (!r.wasSuccessful()) {
            System.exit(1);
        }
    }

    private static final class NameFilter extends Filter {
        private final String pattern;

        private NameFilter(String pattern) {
            this.pattern = pattern.toLowerCase();
        }

        @Override
        public boolean shouldRun(Description description) {
            return description.getMethodName().toLowerCase().contains(pattern);
        }

        @Override
        public String describe() {
            return "Filter contains " + pattern;
        }
    }

}
