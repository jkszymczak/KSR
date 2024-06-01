package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.LinkedList;
import java.util.List;

public class TwoSubjectSummaryThird extends TwoSubjectSummarySecond{
    public TwoSubjectSummaryThird(Subject first, Subject second, FuzzyQuantifier quantifier, SummarizerQualifier summarizer, SummarizerQualifier qualifier, List<BlockGroup> all) {
        super(first, second, quantifier, summarizer, qualifier, all);
    }

    private Double calculateDegreeOfTruth(QuantifierLabel quantifier){
        SummarizerQualifier anded = summarizerFirst.and(qualifier);
        double nominator = anded.cardinal()/(double)firstSubject.size();
        double denominator = (anded.cardinal()/(double)firstSubject.size())+(summarizerSecond.cardinal()/(double)secondSubject.size());
        return quantifier.evaluate(nominator/denominator);
//        return 0.5;
    }

    @Override
    public List<Pair<String, Double>> generateSummaries() {
        List<Pair<String, Double>> results= new LinkedList<>();
        quantifier.getLabels().forEach(quantifier -> {
            String label = quantifier.getLabel() + " Block Groups " + this.second.label+ ", which are "+this.qualifier.getLabel()+ ", compared to Block Groups " + this.first.label + ", are "+this.summarizerFirst.getLabel();
            results.add(new Pair<>(label, this.calculateDegreeOfTruth(quantifier)));
        });
        return results;
    }
}
