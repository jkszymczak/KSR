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
    private Double degreeOfTruth;
    private List<Double> qualityMeasures;

    public LinguisticSummary(SummarizerQualifier summarizer, SummarizerQualifier qualifier,
                             QuantifierLabel quantifierLabel, String label,
                             QuantifierType quantifierType, Double degreeOfTruth) {
        this.summarizer = summarizer;
        this.qualifier = qualifier;
        this.quantifierLabel = quantifierLabel;
        this.quantifierType = quantifierType;
        this.label = label;
        this.degreeOfTruth = degreeOfTruth;
        this.qualityMeasures = new ArrayList<>();
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

    public List<Double> getQualityMeasures() {
        return qualityMeasures;
    }

    public Double getDegreeOfTruth() {
        return degreeOfTruth;
    }

    // remember to make on it calculateQualityMeasures from LinguisticSummary
    public void setQualityMeasures(List<Double> qualityMeasures) {
        this.qualityMeasures = qualityMeasures;
    }

    @Override
    public String toString() {
        return "LinguisticSummary{" +
                "label='" + label + '\'' +
                ", degree of truth= " + degreeOfTruth +
                ", Quality measures= "+ this.qualityMeasures.toString();
//                ", summarizer=" + summarizer +
//                ", qualifier=" + qualifier +
//                ", quantifierLabel=" + quantifierLabel +
//                '}';
    }
}
