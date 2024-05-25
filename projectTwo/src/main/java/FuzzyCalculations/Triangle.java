package FuzzyCalculations;

import org.example.Pair;

public class Triangle implements MembershipFunction{
    double a, b, c;
    Trapezoidal func;
    Pair<Double,Double> range;

    @Override
    public double evaluate(double x) {
        return func.evaluate(x);
    }

    @Override
    public double field() {
        return 0.5*(range.second - range.first) * this.evaluate(b);
    }

    @Override
    public Pair<Double, Double> getRange() {
        return this.range;
    }

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.func = new Trapezoidal(a,b,b,c);
        this.range = new Pair<>(a,c);
    }
}
