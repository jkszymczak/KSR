package LinguisticSummarization;

import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.QuantifierType;
import FuzzyCalculations.SummarizerQualifier;

import java.util.ArrayList;
import java.util.List;

public class LinguisticSummary {
    private SummarizerQualifier summarizer;

    public void setQualifier(SummarizerQualifier qualifier) {
        this.qualifier = qualifier;
    }

    private SummarizerQualifier qualifier;
    private QuantifierLabel quantifierLabel;
    private QuantifierType quantifierType;
    private String label;
    private Double degreeOfTruth;
    private List<Double> qualityMeasures;
    private LinguisticSummaryType linguisticSummaryType;

    public LinguisticSummaryType getLinguisticSummaryType() {
        return linguisticSummaryType;
    }

    public LinguisticSummary(SummarizerQualifier summarizer, SummarizerQualifier qualifier,
                             QuantifierLabel quantifierLabel, String label,
                             QuantifierType quantifierType, LinguisticSummaryType linguisticSummaryType, Double degreeOfTruth) {
        this.summarizer = summarizer;
        this.qualifier = qualifier;
        this.quantifierLabel = quantifierLabel;
        this.quantifierType = quantifierType;
        this.label = label;
        this.degreeOfTruth = degreeOfTruth;
        this.qualityMeasures = new ArrayList<>();
        this.linguisticSummaryType = linguisticSummaryType;
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
        return label + ". [ " + degreeOfTruth + " ]";
    }

    public static String toStringDetailed(List<LinguisticSummary> summaries) {
        StringBuilder wholeText = new StringBuilder();
        wholeText.append(" ");
        for (int i = 0; i < summaries.getFirst().getQualityMeasures().size(); i++) {
            if (i == summaries.getFirst().getQualityMeasures().size() - 1) {
                wholeText.append("|     T").append("    |");

            } else if (i >= 9) {
                wholeText.append("|  T ").append(i+1).append("  ");
            } else {
                wholeText.append("|   T ").append(i+1).append("   ");
            }
        }
        wholeText.append("\n");


        for (LinguisticSummary summary : summaries) {
            StringBuilder text = new StringBuilder();

            for (int i = 0; i < summary.getQualityMeasures().size(); i++) {
                text.append(" | ").append("[").append(String.format("%.2f", summary.getQualityMeasures().get(i))).append("]");
            }
            wholeText.append(text).append(" |\n");
        }
        return wholeText.toString();
    }

    public String toStringFull() {
        return "LinguisticSummary{" +
                "label='" + label + '\'' +
                ", degree of truth= " + degreeOfTruth +
                ", Quality measures= " + this.qualityMeasures.toString() +
                ", summarizer=" + summarizer +
                ", qualifier=" + qualifier +
                ", quantifierLabel=" + quantifierLabel +
                '}';
    }
}
