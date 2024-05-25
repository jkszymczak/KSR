package FuzzyCalculations;

import org.example.Pair;

public interface MembershipFunction {
    double evaluate(double x);
    double field();
    Pair<Double,Double> getRange();
}
