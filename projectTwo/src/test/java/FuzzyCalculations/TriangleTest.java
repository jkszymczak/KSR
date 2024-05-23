package FuzzyCalculations;

import junit.framework.TestCase;

public class TriangleTest extends TestCase {

    public void testEvaluate() {
        Triangle test = new Triangle(0,10,20);
        assertEquals(1.0,test.evaluate(10),0.01);
        assertEquals(0.0,test.evaluate(0),0.01);
        assertEquals(0.0,test.evaluate(20),0.01);
    }
}