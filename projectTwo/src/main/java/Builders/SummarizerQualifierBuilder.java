package Builders;

import FuzzyCalculations.FuzzySet;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

public class SummarizerQualifierBuilder implements Builder<LinguisticSummaryBuilder, SummarizerQualifier> {
    private LinguisticSummaryBuilder upper;
    private Boolean isSummerizer;
    private FuzzySet fuzzySet;
    private Pair<Double,Double> range;


    public SummarizerQualifierBuilder(LinguisticSummaryBuilder upper, Boolean isSummerizer) {
        this.upper = upper;
        this.isSummerizer = isSummerizer;
    }

    public SummarizerQualifierBuilder(Boolean isSummerizer) {
        this.isSummerizer = isSummerizer;
    }
    public SummarizerQualifierBuilder() {

    }

    public FuzzySetBuilder createFuzzySet() {
        return new FuzzySetBuilder(this);
    }

    public static SummarizerQualifierBuilder builder(Boolean isSummerizer){
        return new SummarizerQualifierBuilder(isSummerizer);
    }
    public static SummarizerQualifierBuilder builder(){
        return new SummarizerQualifierBuilder();
    }
    public SummarizerQualifierBuilder onRange(double start,double stop){
        this.range = new Pair<>(start,stop);
        return this;
    }

    @Override
    public LinguisticSummaryBuilder build() {
        SummarizerQualifier builded = this.end();
        return ((this.isSummerizer)? this.upper.withSummarizator(builded) : this.upper.withQualifier(builded));
    }

    @Override
    public SummarizerQualifier end() {
        this.fuzzySet.getMembershipFunction().setRange(this.range);
        return new SummarizerQualifier(this.fuzzySet);

    }

    public SummarizerQualifierBuilder withFuzzySet(FuzzySet fuzzySet){
        this.fuzzySet = fuzzySet;
        return this;
    }

}
