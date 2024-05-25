package Builders;

import FuzzyCalculations.FuzzySet;
import FuzzyCalculations.SummarizerQualifier;

public class SummarizerQualifierBuilder implements Builder<LinguisticSummaryBuilder, SummarizerQualifier> {
    private LinguisticSummaryBuilder upper;
    private Boolean isSummerizer;
    private FuzzySet fuzzySet;


    public SummarizerQualifierBuilder(LinguisticSummaryBuilder upper, Boolean isSummerizer) {
        this.upper = upper;
        this.isSummerizer = isSummerizer;
    }

    public SummarizerQualifierBuilder(Boolean isSummerizer) {
        this.isSummerizer = isSummerizer;
    }

    public FuzzySetBuilder createFuzzySet() {
        return new FuzzySetBuilder(this);
    }

    public static SummarizerQualifierBuilder builder(Boolean isSummerizer){
        return new SummarizerQualifierBuilder(isSummerizer);
    }

    @Override
    public LinguisticSummaryBuilder build() {
        SummarizerQualifier builded = this.end();
        return ((this.isSummerizer)? this.upper.withSummarizator(builded) : this.upper.withQualifier(builded));
    }

    @Override
    public SummarizerQualifier end() {
        return new SummarizerQualifier(this.fuzzySet);

    }

    public SummarizerQualifierBuilder withFuzzySet(FuzzySet fuzzySet){
        this.fuzzySet = fuzzySet;
        return this;
    }

}
