package FuzzyCalculations;

import org.example.Pair;

public class SetMembership implements MembershipFunction{
    private Pair<Double,Double> range;
    private Pair<Double,Double> supp;



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
        return this.supp;
    }

    @Override
    public Pair<Double, Double> getRange() {
        return this.range;
    }

    @Override
    public void setRange(Pair<Double, Double> range) {
        this.range=range;
        this.supp = range;
    }
}
