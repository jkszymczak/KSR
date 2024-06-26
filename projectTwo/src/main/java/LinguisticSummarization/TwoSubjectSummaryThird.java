package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.LinkedList;
import java.util.List;

public class TwoSubjectSummaryThird extends TwoSubjectSummaryFirst {

    private SummarizerQualifier qualifier;
    private String qualifierConjunction;

    public TwoSubjectSummaryThird(Subject first, Subject second, FuzzyQuantifier quantifier,
                                  SummarizerQualifier summarizer, SummarizerQualifier qualifier,
                                  List<BlockGroup> all, String summarizerConjunction, String qualifierConjunction) {
        super(first,second,quantifier,summarizer,all,summarizerConjunction);
        this.qualifier = qualifier;
        this.qualifierConjunction = qualifierConjunction;

    }

    private Double calculateDegreeOfTruth(QuantifierLabel quantifier) {
        SummarizerQualifier anded = summarizerFirst.and(qualifier);
        double nominator = anded.cardinal() / (double) firstSubject.size();
        double denominator = (anded.cardinal() / (double) firstSubject.size()) + (summarizerSecond.cardinal() / (double) secondSubject.size());
        return quantifier.evaluate((denominator==0.0)? 0: nominator / denominator);
//        return 0.5;
    }

    @Override
    public List<Pair<String, Double>> generateSummaries() {
        List<Pair<String, Double>> results = new LinkedList<>();
        quantifier.getLabels().forEach(quantifier -> {
            String label = quantifier.getLabel() + " Block Groups " + this.first.label + " " + this.qualifierConjunction + " " + this.qualifier.getLabel() + " compared to Block Groups " + this.second.label + " " + this.summarizerConjunction + " " + this.summarizerFirst.getLabel();
            results.add(new Pair<>(label, this.calculateDegreeOfTruth(quantifier)));
        });
        return results;
    }
}
