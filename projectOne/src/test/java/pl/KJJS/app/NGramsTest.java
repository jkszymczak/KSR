package pl.KJJS.app;

import junit.framework.TestCase;

public class NGramsTest extends TestCase {

    public void testF() {
        assertEquals(0.056,NGrams.f(7,2,5),0.001);
    }


    public void testSplitToSubstring() {
        String w = "germany";
        String[] expectedResults = {"ge","er","rm","ma","an","ny"};
        String[] results = NGrams.splitToSubstring(w,2);
        for (int i=0;i<results.length;i++){
            assertTrue(results[i].equals(expectedResults[i]));
        }
    }


    public void testCalculateDistance() {
        String w1 = "canada";
        String w2 = "japan";
        double result = NGrams.calculateDistance(w1,w2);
        assertEquals(result,0.929,0.001);
    }
    public void testCommutativity() {
        String w1 = "canada";
        String w2 = "japan";
        assertEquals(NGrams.calculateDistance(w1,w2),NGrams.calculateDistance(w2,w1),0.001);
    }
    public void testReturnable(){
        String w1 = "canada";
        assertEquals(NGrams.calculateDistance(w1,w1),0.0);
    }
}