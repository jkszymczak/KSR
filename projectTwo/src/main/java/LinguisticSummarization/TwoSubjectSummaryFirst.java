package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.LinkedList;
import java.util.List;

//TODO zmodyfikować 1,2 i 3 forme by wykorzystywały że ich forma jest praktycznie identyczna
public class TwoSubjectSummaryFirst extends TwoSubjectSummaryForth{

    FuzzyQuantifier quantifier;

    public TwoSubjectSummaryFirst(Subject first, Subject second, FuzzyQuantifier quantifier, SummarizerQualifier summarizer, List<BlockGroup> all, String summarizerConjunction) {
        super(first, second, summarizer, all, summarizerConjunction);
        this.quantifier = quantifier;
    }


    private Double calculateDegreeOfTruth(QuantifierLabel quantifier) {
        double nominator = summarizerFirst.cardinal() / (double) firstSubject.size();
        double denominator = (summarizerFirst.cardinal() / (double) firstSubject.size()) + (summarizerSecond.cardinal() / (double) secondSubject.size());
        return quantifier.evaluate(nominator / denominator);
//        return 1.0;
    }

    @Override
    public List<Pair<String, Double>> generateSummaries() {
        List<Pair<String, Double>> results = new LinkedList<>();
        quantifier.getLabels().forEach(quantifier -> {
            String label = quantifier.getLabel() + " Block Groups " + this.first.label + " compared to Block Groups " + this.second.label + " " + this.summarizerConjunction + " " + this.summarizerFirst.getLabel();
            results.add(new Pair<>(label, calculateDegreeOfTruth(quantifier)));
        });
        return results;
    }
}
