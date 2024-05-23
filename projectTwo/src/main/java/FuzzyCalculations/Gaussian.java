package FuzzyCalculations;

public class Gaussian implements MembershipFunction{
    double sigma;
    double mean;

    @Override
    public double evaluate(double x) {
        return Math.exp(-0.5* (Math.pow((x-mean)/sigma,2)));
    }

    public Gaussian(double mean, double sigma) {
        this.mean = mean;
        this.sigma = sigma;
    }
}
