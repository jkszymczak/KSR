package FuzzyCalculations;

import org.example.Pair;

public class Triangle implements MembershipFunction{
    double a, b, c;
    Trapezoidal func;

    Pair<Double,Double> supp;
    Pair<Double, Double> range;

    @Override
    public double evaluate(double x) {
        return func.evaluate(x);
    }

    @Override
    public double field() {
        return 0.5*(supp.second - supp.first) * this.evaluate(b);
    }

    @Override
    public Pair<Double, Double> getSupport() {
        return this.supp;
    }

    @Override
    public Pair<Double, Double> getRange() {
        return this.range;
    }

    public Triangle(double a, double b, double c,double start,double end) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.func = new Trapezoidal(a,b,b,c,start,end);
        this.supp = this.func.getSupport();
        this.range = this.func.getRange();
    }
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.func = new Trapezoidal(a,b,b,c);
        this.supp = this.func.getSupport();

    }
    public void setRange(Pair<Double,Double> range){
        this.range=range;
    }
}
