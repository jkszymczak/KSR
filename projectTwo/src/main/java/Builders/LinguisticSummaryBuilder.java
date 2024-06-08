package Builders;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.SummarizerQualifier;
import LinguisticSummarization.LinguisticSummaryGenerator;
import LinguisticSummarization.LinguisticSummaryType;
import LinguisticSummarization.QualityMeasures;
import LinguisticSummarization.Subject;

import java.util.List;
import java.util.Objects;

public class LinguisticSummaryBuilder implements Builder<LinguisticSummaryGenerator, LinguisticSummaryGenerator> {
    private FuzzyQuantifier quantifier;
    private SummarizerQualifier summarizator;
    private SummarizerQualifier qualifier;
    private String summarizatorConjunction;
    private String qualifierConjunction;
    private String subjectText;
    private QualityMeasures qualityMeasures;
    private LinguisticSummaryType linguisticSummaryType;
    private int blockGroupCount;
    private List<BlockGroup> candidates;
    private Subject subject;

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
        this.subjectText = subject;
        return this;
    }

    public LinguisticSummaryBuilder onSubject(Subject subject){
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
    public LinguisticSummaryBuilder onSet(List<BlockGroup> candidates){
        this.candidates = candidates;
        return this;
    }
    public LinguisticSummaryBuilder withBlockCount(int count){
        this.blockGroupCount=count;
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

    private List<BlockGroup> filterSubject(){
        return this.candidates.stream().filter(c -> Objects.equals(c.getLabel(), this.subject.label)).toList();
    }
    @Override
    public LinguisticSummaryGenerator build() {
        List<BlockGroup> newCandidates = (subject!=null)? this.filterSubject() : this.candidates;
        System.out.println("size of new candidates "+newCandidates.size());
        if(this.linguisticSummaryType==LinguisticSummaryType.First){
            return new LinguisticSummaryGenerator(newCandidates,this.quantifier
                    ,this.summarizator.filterSummarizer(subject),
                    this.summarizatorConjunction,
                    this.subjectText);
        }
        return new LinguisticSummaryGenerator(this.quantifier, this.summarizator.filterSummarizer(this.subject),
                this.qualifier.filterSummarizer(this.subject),this.summarizatorConjunction,
                this.qualifierConjunction,this.subjectText,
                this.linguisticSummaryType,newCandidates);

    }

    @Override
    public LinguisticSummaryGenerator end() {
        return null;
    }

}
