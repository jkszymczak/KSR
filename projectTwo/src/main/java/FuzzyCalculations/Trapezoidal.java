package FuzzyCalculations;

public class Trapezoidal implements MembershipFunction{
    double a, b, c,d;
    @Override
    public double evaluate(double x) {
        if (a < x && x < b) return (x - a) / (b - a);
        if(b <= x && x <= c) return 1.0;
        if( c < x && x < d) return (d - x) / (d - c);
        return 0.0;
    }

    public Trapezoidal(double a,double b,double c,double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
