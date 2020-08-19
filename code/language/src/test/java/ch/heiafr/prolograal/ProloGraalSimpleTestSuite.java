package ch.heiafr.prolograal;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ProloGraalTestRunner.class)
@ProloGraalTestSuite({"tests"})
public class ProloGraalSimpleTestSuite {

    public static void main(String[] args) throws Exception {
        ProloGraalTestRunner.runInMain(ProloGraalSimpleTestSuite.class, args);
    }

    /*
     * Our "mx unittest" command looks for methods that are annotated with @Test. By just defining
     * an empty method, this class gets included and the test suite is properly executed.
     */
    @Test
    public void unittest() {
    }
}