package FuzzyCalculations;

import org.example.Pair;

public class QuantifierLabel extends Label{

    public QuantifierLabel(MembershipFunction membershipFunction, String label) {
        super(membershipFunction,label);
    }

    public double evaluate(double value){
        return membershipFunction.evaluate(value);
    }
    public Pair<Double, Double> getSupport() {
        return this.membershipFunction.getSupport();
    }
    public Pair<Double, Double> getRange() {
        return this.membershipFunction.getRange();
    }
}
