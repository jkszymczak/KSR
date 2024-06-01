package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.LinkedList;
import java.util.List;

public class TwoSubjectSummarySecond extends TwoSubjectSummaryFirst{

    SummarizerQualifier qualifier;

    public TwoSubjectSummarySecond(Subject first, Subject second, FuzzyQuantifier quantifier, SummarizerQualifier summarizer, SummarizerQualifier qualifier, List<BlockGroup> all) {
        super(first, second,quantifier, summarizer, all);
        this.qualifier = qualifier.filterSummarizer(second.label);
    }
    private Double calculateDegreeOfTruth(QuantifierLabel quantifier){
        double nominator = summarizerFirst.cardinal()/(double)firstSubject.size();
        double denominator = (summarizerFirst.cardinal()/(double)firstSubject.size())+(summarizerSecond.and(this.qualifier).cardinal()/(double)secondSubject.size());
        return quantifier.evaluate(nominator/denominator);
//        return 0.5;
    }

    @Override
    public List<Pair<String, Double>> generateSummaries() {
        List<Pair<String, Double>> results= new LinkedList<>();
        quantifier.getLabels().forEach(quantifier -> {
            String label = quantifier.getLabel() + " Block Groups " + this.first.label + " compared to Block Groups " + this.second.label + ", which are "+this.qualifier.getLabel()+", are "+this.summarizerFirst.getLabel();
            results.add(new Pair<>(label, calculateDegreeOfTruth(quantifier)));
        });
        return results;
    }
}
