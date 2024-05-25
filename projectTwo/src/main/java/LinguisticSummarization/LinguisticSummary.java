package LinguisticSummarization;

import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.QuantifierType;
import FuzzyCalculations.SummarizerQualifier;

import java.util.ArrayList;
import java.util.List;

public class LinguisticSummary {
    private SummarizerQualifier summarizer;
    private SummarizerQualifier qualifier;
    private QuantifierLabel quantifierLabel;
    private QuantifierType quantifierType;
    private String label;
    private List<Double> qualityMeasures;

    public LinguisticSummary(SummarizerQualifier summarizer, SummarizerQualifier qualifier,
                             QuantifierLabel quantifierLabel, String label, Double degreeOfTruth,
                             QuantifierType quantifierType) {
        this.summarizer = summarizer;
        this.qualifier = qualifier;
        this.quantifierLabel = quantifierLabel;
        this.quantifierType = quantifierType;
        this.label = label;
        this.qualityMeasures = new ArrayList<>();
        this.qualityMeasures.add(degreeOfTruth);
    }

    public SummarizerQualifier getSummarizer() {
        return summarizer;
    }

    public SummarizerQualifier getQualifier() {
        return qualifier;
    }

    public QuantifierLabel getQuantifierLabel() {
        return quantifierLabel;
    }

    public QuantifierType getQuantifierType() {
        return quantifierType;
    }

    public String getLabel() {
        return label;
    }

    public Double getDegreeOfTruth() {
        return qualityMeasures.getFirst();
    }

    public Double getT() {
        //Remember to made first setQualityMeasures and addTMeasure
        return qualityMeasures.getLast();
    }

    public List<Double> getQualityMeasures() {
        return qualityMeasures;
    }

    public void setQualityMeasures(List<Double> qualityMeasures) {
        this.qualityMeasures = qualityMeasures;
    }

    public void addTMeasure(double tMeasure) {
        this.qualityMeasures.add(tMeasure);
    }

    // TODO repair
    @Override
    public String toString() {
        return "LinguisticSummary{" +
                "label='" + label + '\'' +
                ", degree of truth= " + qualityMeasures.get(0) +
                ", summarizer=" + summarizer +
                ", qualifier=" + qualifier +
                ", quantifierLabel=" + quantifierLabel +
                '}';
    }
}
