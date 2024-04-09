package pl.KJJS.app;

import junit.framework.TestCase;

public class NGramsTest extends TestCase {

    public void testF() {
    }

    public void testSplitToSubstring() {
        String w = "germany";
        String[] expectedResults = {"ge","er","rm","ma","an","ny"};
        String[] results = NGrams.splitToSubstring(w,2);
        for (int i=0;i<results.length;i++){
            assertTrue(results[i].equals(expectedResults[i]));
        }


//        assertEquals();
    }

    public void testCalculateDistance() {
    }
}