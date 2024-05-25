package FuzzyCalculations;

import org.example.Pair;

public class SetMembership implements MembershipFunction{

    @Override
    public double evaluate(double x) {
        return 1.0;
    }

    @Override
    public double field() {
        return 0;
    }

    @Override
    public Pair<Double, Double> getSupport() {
        return null;
    }

    @Override
    public Pair<Double, Double> getRange() {
        return null;
    }

    @Override
    public void setRange(Pair<Double, Double> range) {

    }
}
