package FuzzyCalculations;

import junit.framework.TestCase;

public class TrapezoidalTest extends TestCase {

    public void testEvaluate() {
        Trapezoidal test = new Trapezoidal(0,10,20,30);
        assertEquals(0.0,test.evaluate(0),0.01);
        assertEquals(1.0,test.evaluate(10),0.01);
        assertEquals(1.0,test.evaluate(20),0.01);
        assertEquals(0.0,test.evaluate(30),0.01);
    }
}