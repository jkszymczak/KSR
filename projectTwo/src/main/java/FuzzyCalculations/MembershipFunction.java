package FuzzyCalculations;

import org.example.Pair;

public interface MembershipFunction {

    double evaluate(double x);
    double field();
    Pair<Double,Double> getSupport();
    Pair<Double,Double> getRange();
    void setRange(Pair<Double,Double> range);
}
