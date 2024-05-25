package FuzzyCalculations;

import org.example.Pair;

public class Trapezoidal implements MembershipFunction{
    double a, b, c,d;

    Pair<Double,Double> supp;
    Pair<Double, Double> range;
    @Override
    public double evaluate(double x) {
        if (a < x && x < b) return (x - a) / (b - a);
        if(b <= x && x <= c) return 1.0;
        if( c < x && x < d) return (d - x) / (d - c);
        return 0.0;
    }

    @Override
    public double field() {
        return 0.5*((c-b)+(d-a))*evaluate(b);
    }

    @Override
    public Pair<Double, Double> getSupport() {
        return this.supp;
    }

    @Override
    public Pair<Double, Double> getRange() {
        return this.range;
    }

    public Trapezoidal(double a,double b,double c,double d,double start,double end) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.supp = new Pair<>(a,d);
        this.range = new Pair<>(start,end);
    }
    public Trapezoidal(double a,double b,double c,double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.supp = new Pair<>(a,d);
    }
    public void setRange(Pair<Double,Double> range){
        this.range=range;
    }
}
