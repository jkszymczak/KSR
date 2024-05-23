package FuzzyCalculations;

public class Triangle implements MembershipFunction{
    double a, b, c;
    Trapezoidal func;

    @Override
    public double evaluate(double x) {
        return func.evaluate(x);
    }

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.func = new Trapezoidal(a,b,b,c);
    }
}
