package FuzzyCalculations;

import Builders.MembershipFunctionBuilder;
import junit.framework.TestCase;

public class MembershipFunctionBuilderTest extends TestCase {

    public void testBuilder(){
        MembershipFunction test = MembershipFunctionBuilder.builder().createTriangle(0,10,20).end();
        assertEquals(1.0,test.evaluate(10.0),0.001);
        assertEquals(0.0,test.evaluate(20.0),0.001);
        assertEquals(0.0,test.evaluate(0.0),0.001);
        assertEquals(0.5,test.evaluate(5.0),0.001);



    }



}