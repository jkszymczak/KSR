package LinguisticSummarization;

import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;

public class LinguisticSummary {
    private SummarizerQualifier summarizer;
    private SummarizerQualifier qualifier;
    private QuantifierLabel quantifierLabel;
    private String label;
    private Double degreeOfTruth;

    public LinguisticSummary(SummarizerQualifier summarizer, SummarizerQualifier qualifier, QuantifierLabel quantifierLabel, String label, Double degreeOfTruth) {
        this.summarizer = summarizer;
        this.qualifier = qualifier;
        this.quantifierLabel = quantifierLabel;
        this.label = label;
        this.degreeOfTruth = degreeOfTruth;
    }



    @Override
    public String toString() {
        return "LinguisticSummary{" +
                "label='" + label + '\'' +
                ", degree of truth= "+ degreeOfTruth +
                ", summarizer=" + summarizer +
                ", qualifier=" + qualifier +
                ", quantifierLabel=" + quantifierLabel +
                '}';
    }
}
