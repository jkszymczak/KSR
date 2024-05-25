package FuzzyCalculations;

import org.example.Pair;

public class Gaussian implements MembershipFunction{
    double sigma;
    double mean;
    Pair<Double,Double> supp;
    Pair<Double, Double> range;

    @Override
    public double evaluate(double x) {
        if(x>=this.supp.second) return 0.0;
        if(x<=this.supp.first) return 0.0;
        return Math.exp(-0.5* (Math.pow((x-mean)/sigma,2)));
    }

    @Override
    public double field() {
        double start = supp.first;
        double stop = supp.second;
        int numSteps = 10000; // number of steps for the numerical integration
        double stepSize = (stop - start) / numSteps;
        double area = 0.0;

        for (int i = 0; i < numSteps; i++) {
            double xLeft = start + i * stepSize;
            double xRight = start + (i + 1) * stepSize;
            area += 0.5 * (evaluate(xLeft) + evaluate(xRight)) * stepSize;
        }

        return area;
    }

    @Override
    public Pair<Double, Double> getSupport() {
        return this.supp;
    }

    @Override
    public Pair<Double, Double> getRange() {
        return this.range;
    }

    public Gaussian(double mean, double sigma,double suppStart,double suppStop,double start,double stop) {
        this.mean = mean;
        this.sigma = sigma;
        this.supp = new Pair<>(suppStart,suppStop);
        this.range = new Pair<>(start,stop);
    }
    public Gaussian(double mean, double sigma,double suppStart,double suppStop) {
        this.mean = mean;
        this.sigma = sigma;
        this.supp = new Pair<>(suppStart,suppStop);

    }
    public void setRange(Pair<Double,Double> range){
        this.range=range;
    }

}
