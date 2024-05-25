package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class LinguisticSummary {
    FuzzyQuantifier quantifier;
    SummarizerQualifier summarizator;
    SummarizerQualifier qualifier;
    String summarizatorConjunction;
    String qualifierConjunction;
    String subject;
    QualityMeasures qualityMeasures ;
    LinguisticSummaryType linguisticSummaryType;

    public FuzzyQuantifier getQuantifier() {
        return quantifier;
    }

    public SummarizerQualifier getSummarizator() {
        return summarizator;
    }

    public SummarizerQualifier getQualifier() {
        return qualifier;
    }

    public String getConjunction() {
        return summarizatorConjunction;
    }

    public QualityMeasures getQualityMeasures() {
        return qualityMeasures;
    }

    public LinguisticSummaryType getLinguisticSummaryType() {
        return linguisticSummaryType;
    }


    public List<Pair<String,Double>> generateSummaries(){
        return this.quantifier.getLabels().stream().map(label -> {
            String summary =(this.linguisticSummaryType == LinguisticSummaryType.First)?
                    label.getLabel()+" "+ this.subject + " " + this.summarizatorConjunction + " " + this.summarizator.getLabel() :
                    label.getLabel() + " " + this.subject + " " + this.qualifierConjunction + " " + this.qualifier.getLabel() +
                            " " + this.summarizatorConjunction + " "+ this.summarizator.getLabel() ;
            Double truth = this.degreeOfTruth(label);
            return new Pair<>(summary, truth);
        }).collect(Collectors.toList());
    }

    public double degreeOfTruth(QuantifierLabel label){
        return this.qualityMeasures.t1(this.summarizator,this.qualifier,label,this.quantifier.getType());
    };

    public LinguisticSummary(FuzzyQuantifier quantifier,
                             SummarizerQualifier summarizator,
                             SummarizerQualifier qualifier,
                             String summarizatorConjunction,
                             String qualifierConjunction,
                             String subject,
                             QualityMeasures qualityMeasures,
                             LinguisticSummaryType linguisticSummaryType) {
        this.quantifier = quantifier;
        this.summarizator = summarizator;
        this.qualifier = qualifier;
        this.summarizatorConjunction = summarizatorConjunction;
        this.qualifierConjunction = qualifierConjunction;
        this.subject = subject;
        this.qualityMeasures = new QualityMeasures();
        this.linguisticSummaryType = linguisticSummaryType;
    }
    public LinguisticSummary(List<BlockGroup> candidates,
                             FuzzyQuantifier quantifier,
                             SummarizerQualifier summarizator,
                             String summarizatorConjunction,
                             String subject,
                             QualityMeasures qualityMeasures) {
        this.quantifier = quantifier;
        this.summarizator = summarizator;
        this.qualifier = qualifier;
        this.summarizatorConjunction = summarizatorConjunction;
        this.qualifierConjunction = qualifierConjunction;
        this.subject = subject;
        this.qualityMeasures = new QualityMeasures();
        this.linguisticSummaryType = linguisticSummaryType;
    }
}
