package ch.heiafr.prolograal.launcher;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class ProloGraalMain {

   private static final String PROLOGRAAL = "pl";

   /**
    * The main entry point.
    */
   public static void main(String[] args) throws IOException {
      Source source;
      Map<String, String> options = new HashMap<>();
      String file = null;
      for (String arg : args) {
         if (!parseOption(options, arg)) {
            if (file == null) {
               file = arg;
            }
         }
      }

      if (file == null) {
         // @formatter:off
         source = Source.newBuilder(PROLOGRAAL, new InputStreamReader(System.in), "<stdin>").build();
         // @formatter:on
      } else {
         source = Source.newBuilder(PROLOGRAAL, new File(file)).build();
      }

      System.exit(executeSource(source, options));
   }

   private static int executeSource(Source source, Map<String, String> options) {
      Context context;
      PrintStream err = System.err;
      try {
         context = Context.newBuilder(PROLOGRAAL).in(System.in).out(System.out).options(options).build();
      } catch (IllegalArgumentException e) {
         err.println(e.getMessage());
         return 1;
      }

      Value prologBoolean1 = context.eval("pl","useinterpreter.");

      //System.out.println("###First test###");
      Value prologBoolean = context.eval("pl","useinterpreter, consultstring('test(10).'), test(A), is(B,'**'(2,A)).");
      System.out.println("Did request success? "+prologBoolean.asBoolean());
      if(prologBoolean.asBoolean()){
         System.out.println("A value: "+prologBoolean.getMember("A").asInt());
         System.out.println("B value: "+prologBoolean.getMember("B").asInt());
      }

      System.out.println("###Second test###");
      Value prologBooleanArray = context.eval("pl","consultstring(~test(a). test2(b).~). test(A). test2(c), test2(B).");
      System.out.println("did 2nd request success? " + prologBooleanArray.getArrayElement(1).asBoolean());
      System.out.println("A value: "+prologBooleanArray.getArrayElement(1).getMember("A").asString());
      System.out.println("did 3nd request success? " + prologBooleanArray.getArrayElement(2).asBoolean());

      //System.out.println("TEST:" + context.eval("pl","consultstring(~test(8). test(10). test2('value'). t(X):-test(X).~), test2(X), test(A), is(B,'**'(2,A)), useinterpreter.").getMember("X").asString());

      //System.out.println("TEST:" + context.eval("pl","trace. consult('C:\\Users\\Tony\\Documents\\Projets\\prolog-truffle\\code\\language\\tests\\07_benchmark.pl'), recommendedN(N), benchmark(N). recommendedN(W).").getArrayElement(2).getMember("W").asInt());

      System.out.println("== running on " + context.getEngine());

      try {
         context.eval(source);
         return 0;
      } catch (PolyglotException ex) {
         if (ex.isInternalError()) {
            // for internal errors we print the full stack trace
            ex.printStackTrace();
         } else {
            err.println(ex.getMessage());
         }
         return 1;
      } finally {
         context.close();
      }
   }

   private static boolean parseOption(Map<String, String> options, String arg) {
      if (arg.length() <= 2 || !arg.startsWith("--")) {
         return false;
      }
      int eqIdx = arg.indexOf('=');
      String key;
      String value;
      if (eqIdx < 0) {
         key = arg.substring(2);
         value = null;
      } else {
         key = arg.substring(2, eqIdx);
         value = arg.substring(eqIdx + 1);
      }

      if (value == null) {
         value = "true";
      }
      options.put(key, value);
      return true;
   }

}
