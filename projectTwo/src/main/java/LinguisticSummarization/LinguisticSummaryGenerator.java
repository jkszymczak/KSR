package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.ArrayList;
import java.util.Comparator;
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
    int blockGroupsCount;

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
                    return new LinguisticSummary(this.summarizator,this.qualifier,label,summary,this.quantifier.getType(),this.linguisticSummaryType,this.degreeOfTruth(label));
        }).collect(Collectors.toList());
    }

    /** Returns summary with highest degree of truth
     * */
    public LinguisticSummary generateBest(){
        List<LinguisticSummary> summaries = this.generateSummaries();
        return summaries.stream().max(Comparator.comparing(s -> s.getDegreeOfTruth())).get();
    }

//    public double degreeOfTruth(LinguisticSummary linguisticSummary) {
//        return this.qualityMeasures.t1(linguisticSummary.getSummarizer(), linguisticSummary.getQualifier(), linguisticSummary.getQuantifierLabel(), linguisticSummary.getQuantifierType());
//    }
public double degreeOfTruth(QuantifierLabel label) {
    return this.qualityMeasures.t1(this.summarizator, this.qualifier, label, this.quantifier.getType());
}

    public void calculateQualityMeasures(List<Double> weights, LinguisticSummary linguisticSummary) {
        linguisticSummary.setQualityMeasures(this.qualityMeasures.all_t(weights, linguisticSummary, 10000));
    }

    private void generateQualifierWholeSet( List<BlockGroup> data) {
        if(this.linguisticSummaryType != LinguisticSummaryType.First) return;
        List<SummarizerQualifier> qualifiers = this.summarizator.getElementalParts().stream().map(summarizer -> summarizer.generateFullSet(data)).toList();
        SummarizerQualifier qualifier = qualifiers.get(0);
        for (int i = 1; i < qualifiers.size(); i++) {
            qualifier = qualifier.and(qualifiers.get(i));
        }
        System.out.println(qualifier);
        this.qualifier = qualifier;
    }

    public double calculateT(List<Double> weights, LinguisticSummary linguisticSummary) {
        return this.qualityMeasures.t(weights, linguisticSummary);
    }

    // This method required that summaries have qualityMeasures
//    public LinguisticSummary calculateOptimalSummary(List<LinguisticSummary> summaries) {
//        List<Double> tValues = new ArrayList<>();
//        for (LinguisticSummary summary : summaries) {
//            tValues.add(summary.getQualityMeasures().getLast());
//        }
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

    public LinguisticSummaryGenerator(FuzzyQuantifier quantifier,
                                      SummarizerQualifier summarizator,
                                      SummarizerQualifier qualifier,
                                      String summarizatorConjunction,
                                      String qualifierConjunction,
                                      String subject,
                                      LinguisticSummaryType linguisticSummaryType,
                                      int blockGroupsCount) {
        this.quantifier = quantifier;
        this.summarizator = summarizator;
        this.qualifier = qualifier;
        this.summarizatorConjunction = summarizatorConjunction;
        this.qualifierConjunction = qualifierConjunction;
        this.subject = subject;
        this.qualityMeasures = new QualityMeasures();
        this.linguisticSummaryType = linguisticSummaryType;
        this.blockGroupsCount = blockGroupsCount;

    }

    public LinguisticSummaryGenerator(List<BlockGroup> candidates,
                                      FuzzyQuantifier quantifier,
                                      SummarizerQualifier summarizator,
                                      String summarizatorConjunction,
                                      String subject
                                      ) {
        this.quantifier = quantifier;
        this.summarizator = summarizator;
        this.summarizatorConjunction = summarizatorConjunction;
//        this.qualifierConjunction = qualifierConjunction;
        this.subject = subject;
        this.qualityMeasures = new QualityMeasures();
        this.linguisticSummaryType = LinguisticSummaryType.First;
        this.blockGroupsCount = candidates.size();
        this.generateQualifierWholeSet(candidates);
    }

}
