package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinguisticSummaryGenerator {
    FuzzyQuantifier quantifier;
    SummarizerQualifier summarizator;
    SummarizerQualifier qualifier;
    String summarizatorConjunction;
    String qualifierConjunction;
    String subject;
    QualityMeasures qualityMeasures;
    LinguisticSummaryType linguisticSummaryType;
    int blockGroupsCount; // TODO: Kuba musimy to uzupełnić

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


//    public List<Pair<String, Double>> generateSummaries() {
//        return this.quantifier.getLabels().stream().map(label -> {
//            String summary = (this.linguisticSummaryType == LinguisticSummaryType.First) ?
//                    label.getLabel() + " " + this.subject + " " + this.summarizatorConjunction + " " + this.summarizator.getLabel() :
//                    label.getLabel() + " " + this.subject + " " + this.qualifierConjunction + " " + this.qualifier.getLabel() +
//                            " " + this.summarizatorConjunction + " " + this.summarizator.getLabel();
//            Double truth = this.degreeOfTruth(label);
//            return new Pair<>(summary, truth);
//        }).collect(Collectors.toList());
//    }

    public List<LinguisticSummary> generateSummaries() {
        return this.quantifier.getLabels().stream().map(label -> {
            String summary = (this.linguisticSummaryType == LinguisticSummaryType.First) ?
                    label.getLabel() + " " + this.subject + " " + this.summarizatorConjunction + " " + this.summarizator.getLabel() :
                    label.getLabel() + " " + this.subject + " " + this.qualifierConjunction + " " + this.qualifier.getLabel() +
                            " " + this.summarizatorConjunction + " " + this.summarizator.getLabel();
                    return new LinguisticSummary(this.summarizator,this.qualifier,label,summary,this.quantifier.getType(),this.degreeOfTruth(label));
        }).collect(Collectors.toList());
    }

//    public double degreeOfTruth(LinguisticSummary linguisticSummary) {
//        return this.qualityMeasures.t1(linguisticSummary.getSummarizer(), linguisticSummary.getQualifier(), linguisticSummary.getQuantifierLabel(), linguisticSummary.getQuantifierType());
//    }
public double degreeOfTruth(QuantifierLabel label) {
    return this.qualityMeasures.t1(this.summarizator, this.qualifier, label, this.quantifier.getType());
}

    public void calculateQualityMeasures(List<Double> weights, LinguisticSummary linguisticSummary) {
        linguisticSummary.setQualityMeasures(this.qualityMeasures.all_t(weights, linguisticSummary, this.blockGroupsCount));
    }

    public double calculateT(List<Double> weights, LinguisticSummary linguisticSummary) {
        return this.qualityMeasures.t(weights, linguisticSummary);
    }

//    public LinguisticSummary calculateOptimalSummary(List<LinguisticSummary> summaries) {
//        List<Double> tValues = new ArrayList<>();
//        for (LinguisticSummary summary : summaries) {
//            tValues.add(summary.getQualityMeasures());
//        }
//        List<Double> tValues = summaries.stream().map(LinguisticSummary::getT).collect(Collectors.toList());
//        int maxIndex = 0;
//        double maxValue = tValues.get(0);
//        for (int i = 1; i < tValues.size(); i++) {
//            if (tValues.get(i) > maxValue) {
//                maxValue = tValues.get(i);
//                maxIndex = i;
//            }
//        }
//        return summaries.get(maxIndex);
//    }
    // TODO musimy dodać opcję generowania pojedyńczego zdania tylko dla jednego kwantyfikatora.
    //  Pod kątem że będziemy chcieli wygenerować zdanie tylko dla najlepszego podsumowania.


    public LinguisticSummaryGenerator(FuzzyQuantifier quantifier,
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

    public LinguisticSummaryGenerator(List<BlockGroup> candidates,
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
