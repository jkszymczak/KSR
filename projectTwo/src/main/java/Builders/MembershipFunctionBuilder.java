package Builders;

import FuzzyCalculations.*;
import org.example.Pair;

public class MembershipFunctionBuilder<T extends SetBuilder<?,?,?>>  implements Builder<T, MembershipFunction> {
    private T upper;
    private MembershipFunction builded;
    private Pair<Double,Double> range;

    public MembershipFunctionBuilder(T upper) {
        this.upper = upper;
    }
    public MembershipFunctionBuilder() {

    }

    public static <T extends SetBuilder<?, ?, ?>>MembershipFunctionBuilder<T> builder(){
        return new MembershipFunctionBuilder<T>();
    }

    public MembershipFunctionBuilder<T> createTriangle(double a, double b, double c) {
        this.builded = new Triangle(a, b, c);
        return this;
    }
    public MembershipFunctionBuilder<T> createTrapezoidal(double a, double b, double c, double d) {
        this.builded = new Trapezoidal(a, b, c,d);
        return this;
    }
    public MembershipFunctionBuilder<T> createGaussian(double mean,double sigma) {
        this.builded = new Gaussian(mean,sigma);
        return this;
    }
    public MembershipFunctionBuilder<T> withRange(Pair<Double,Double> range){
        this.range = range;
        return this;
    }

    public MembershipFunction end(){
        return builded;
    }

    @Override
    public T build() {
        return (T) this.upper.withMembershipFuntion(this.builded);
    }
}
