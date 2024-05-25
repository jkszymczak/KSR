package Builders;

import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.SummarizerQualifier;
import LinguisticSummarization.LinguisticSummaryGenerator;
import LinguisticSummarization.LinguisticSummaryType;
import LinguisticSummarization.QualityMeasures;

public class LinguisticSummaryBuilder implements Builder<LinguisticSummaryGenerator, LinguisticSummaryGenerator> {
    private FuzzyQuantifier quantifier;
    private SummarizerQualifier summarizator;
    private SummarizerQualifier qualifier;
    private String summarizatorConjunction;
    private String qualifierConjunction;
    private String subject;
    private QualityMeasures qualityMeasures;
    private LinguisticSummaryType linguisticSummaryType;

    public static LinguisticSummaryBuilder builder() {
        return new LinguisticSummaryBuilder();
    }

    public LinguisticSummaryBuilder withQuantifier(FuzzyQuantifier quantifier) {
        this.quantifier = quantifier;
        return this;
    }

    public LinguisticSummaryBuilder withSummarizator(SummarizerQualifier summarizator) {
        this.summarizator = summarizator;
        return this;
    }

    public LinguisticSummaryBuilder withQualifier(SummarizerQualifier qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public LinguisticSummaryBuilder withSummarizatorConjunction(String summarizatorConjunction) {
        this.summarizatorConjunction = summarizatorConjunction;
        return this;
    }

    public LinguisticSummaryBuilder withQualifierConjunction(String qualifierConjunction) {
        this.qualifierConjunction = qualifierConjunction;
        return this;
    }

    public LinguisticSummaryBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public LinguisticSummaryBuilder withQualityMeasures(QualityMeasures qualityMeasures) {
        this.qualityMeasures = qualityMeasures;
        return this;
    }

    public LinguisticSummaryBuilder withLinguisticSummaryType(LinguisticSummaryType linguisticSummaryType) {
        this.linguisticSummaryType = linguisticSummaryType;
        return this;
    }

    public SummarizerQualifierBuilder createSummerizer() {
        return new SummarizerQualifierBuilder(this,true);
    }
    public SummarizerQualifierBuilder createQualifier() {
        return new SummarizerQualifierBuilder(this,false);
    }
    public FuzzyQuantifierBuilder createQuantifier() {
        return new FuzzyQuantifierBuilder(this);
    }

    @Override
    public LinguisticSummaryGenerator build() {
        return new LinguisticSummaryGenerator(this.quantifier, this.summarizator,
                this.qualifier,this.summarizatorConjunction,
                this.qualifierConjunction,this.subject,
                this.qualityMeasures,this.linguisticSummaryType);

    }

    @Override
    public LinguisticSummaryGenerator end() {
        return null;
    }

}
