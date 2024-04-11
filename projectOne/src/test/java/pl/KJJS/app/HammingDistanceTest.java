package pl.KJJS.app;

import junit.framework.TestCase;
import pl.KJJS.app.metrics.HammingDistance;

public class HammingDistanceTest extends TestCase {

    public void testCalculateDistance() {
        Boolean[] v1 = {true,true,false,false,true,true};
        Boolean[] v2 = {false,true,true,true,true,false};
        assertEquals(HammingDistance.calculateDistance(v1,v2),4);
    }
    public void testCommutativity(){
        Boolean[] v1 = {true,true,false,false,true,true};
        Boolean[] v2 = {false,true,true,true,true,false};
        assertEquals(HammingDistance.calculateDistance(v1,v2),HammingDistance.calculateDistance(v2,v1));
    }
    public void testSomething(){
        Boolean[] v1 = {true,true,false,false,true,true};
        assertEquals(HammingDistance.calculateDistance(v1,v1),0);
    }
}