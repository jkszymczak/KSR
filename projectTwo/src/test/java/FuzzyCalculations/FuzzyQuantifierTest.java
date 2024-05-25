package FuzzyCalculations;

import Builders.QuantifierLabelBuilder;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class FuzzyQuantifierTest extends TestCase {

    public void testCalculateMemberships() {
        MembershipFunction func1 = new Triangle(0,10,20);
        MembershipFunction func2 = new Trapezoidal(10,20,30,40);
        QuantifierLabel ql = new QuantifierLabel(func1,"Nie wiem");
        QuantifierLabel ql2 = new QuantifierLabel(func2,"Też nie wiem");
        List<QuantifierLabel> l = new LinkedList<>();
        l.add(ql);
        l.add(ql2);
        FuzzyQuantifier fq = new FuzzyQuantifier(l,QuantifierType.absolute);
        assertEquals(0.0,fq.calculateMemberships(20).get(0).second,0.001);
        assertEquals(1.0,fq.calculateMemberships(20).get(1).second,0.001);
        assertEquals(fq.calculateMemberships(15).get(0).second,fq.calculateMemberships(15).get(1).second,0.001);
    }
    public void testBuilder(){
        QuantifierLabel test = QuantifierLabelBuilder.builder().createMembershipFunction().createTriangle(0,10,20).build().withLabel("mało").end();
        System.out.println(test.label);
    }
}