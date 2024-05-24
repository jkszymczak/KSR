package FuzzyCalculations;

import junit.framework.TestCase;

public class QuantifierLabelTest extends TestCase {

    public void testEvaluateMembership() {
        MembershipFunction func1 = new Triangle(0,10,20);
//        MembershipFunction func2 = new Trapezoidal(10,20,30,40);
        QuantifierLabel ql = new QuantifierLabel(func1,"Nie wiem");
        assertEquals(1.0,ql.evaluate(10),0.001);
    }
}