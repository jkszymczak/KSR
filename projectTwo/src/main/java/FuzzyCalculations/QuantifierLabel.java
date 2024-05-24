package FuzzyCalculations;

public class QuantifierLabel extends Label{

    public QuantifierLabel(MembershipFunction membershipFunction, String label) {
        super(membershipFunction,label);
    }

    public double evaluate(double value){
        return membershipFunction.evaluate(value);
    }
}
