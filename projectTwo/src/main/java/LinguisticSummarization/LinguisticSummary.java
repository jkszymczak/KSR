package LinguisticSummarization;

import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.SummarizerQualifier;

public class LinguisticSummary {
    FuzzyQuantifier quantifier;
    SummarizerQualifier summarizator;
    SummarizerQualifier qualifier;
    String conjunction;
    QualityMeasures qualityMeasures;
    public String[] generateSummaries(){
        return null;
    }
    public double degreeOfTruth(){
        return 0;
    };
    public LinguisticSummary builder(){
        return new LinguisticSummary();
    }
    public LinguisticSummary type(LinguisticSummaryType type){
        return null;
    }

    public LinguisticSummary qualifier(SummarizerQualifier qualifier){
        return null;
    }
}
