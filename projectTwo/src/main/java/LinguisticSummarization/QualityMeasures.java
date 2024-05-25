package LinguisticSummarization;

import FuzzyCalculations.*;
import org.example.Pair;

import java.util.ArrayList;
import java.util.List;

public class QualityMeasures {
    double r(SummarizerQualifier summarizer, SummarizerQualifier qualifier, QuantifierType quantifierType) {
        SummarizerQualifier joined = summarizer.and(qualifier);
        Double summaryzatorSum = 0.0;
        for (Member m : joined.getElements().values()) {
            summaryzatorSum += m.getMembership();
        }
        if (quantifierType == QuantifierType.absolute) {
            return summaryzatorSum;
        }

        Double qualifierSum = 0.0;
        for (Member m : qualifier.getElements().values()) {
            qualifierSum += m.getMembership();
        }
        return summaryzatorSum / qualifierSum;
    }

    double t1(SummarizerQualifier summarizer, SummarizerQualifier qualifier, QuantifierLabel qualifierLabel, QuantifierType quantifierType) {
        double r = this.r(summarizer, qualifier, quantifierType);
        return qualifierLabel.evaluate(r);
    }

    double t2(SummarizerQualifier summarizer) {
        double in = 1.;
        for (SummarizerQualifier single_summarizer : summarizer.getElementalParts()) {
            Pair<Double, Double> supp = single_summarizer.getSupport();
            Pair<Double, Double> range = single_summarizer.getRange();
            in *= (supp.second - supp.first) / (range.second - range.first);
        }
        double geometric_mean = Math.pow(in, 1.0 / summarizer.getElementalParts().size());
        return 1 - geometric_mean;
    }

    double t3(SummarizerQualifier summarizer, SummarizerQualifier qualifier) {
        int numerator = summarizer.and(qualifier).getElements().size();
        int denominator = qualifier.getElements().size();
        return (double) numerator / (double) denominator;
    }

    double t4(SummarizerQualifier summarizer, SummarizerQualifier qualifier, int allBlockGroups) {
        double r = 1.;
        for (SummarizerQualifier single_summarizer : summarizer.getElementalParts()) {
            int numerator = single_summarizer.getElements().size();
            r *= (double) numerator / (double) allBlockGroups;
        }
        return Math.abs(r - t3(summarizer, qualifier));
    }

    double t5(SummarizerQualifier summarizer) {
        return 2 * Math.pow(0.5, summarizer.getElementalParts().size());
    }

    double t6(QuantifierLabel quantifier) {
        Pair<Double, Double> supp = quantifier.getSupport();
        Pair<Double, Double> range = quantifier.getRange();
        double in = (supp.second - supp.first) / (range.second - range.first);
        return 1 - in;
    }

    double t7(QuantifierLabel quantifier) {
        return 1 - quantifier.getMembershipFunction().field();
    }

    double t8(SummarizerQualifier summarizer) {
        double accumulator = 1.0;
        for (SummarizerQualifier summ : summarizer.getElementalParts()) {
            double x = summ.getRange().second - summ.getRange().first;
            accumulator *= summ.cardinal() / x;
        }
        return Math.pow(accumulator, 1 / summarizer.getElementalParts().size());
    }

    double t9(SummarizerQualifier qualifier) {
        return this.t2(qualifier);
    }

    double t10(SummarizerQualifier qualifier) {
        return this.t8(qualifier);
    }

    double t11(SummarizerQualifier qualifier) {
        return this.t5(qualifier);
    }

    List<Double> all_t(LinguisticSummaryGenerator linguisticSummaryGenerator, QuantifierLabel qualifierLabel, int allBlockGroups) {
        List<Double> results = new ArrayList<>();
        results.add(t1(linguisticSummaryGenerator.summarizator, linguisticSummaryGenerator.qualifier, qualifierLabel, linguisticSummaryGenerator.quantifier.getType()));
        results.add(t2(linguisticSummaryGenerator.summarizator));
        results.add(t3(linguisticSummaryGenerator.summarizator, linguisticSummaryGenerator.qualifier));
        results.add(t4(linguisticSummaryGenerator.summarizator, linguisticSummaryGenerator.qualifier, allBlockGroups));
        results.add(t5(linguisticSummaryGenerator.summarizator));
        results.add(t6(qualifierLabel));
        results.add(t7(qualifierLabel));
        results.add(t8(linguisticSummaryGenerator.summarizator));
        results.add(t9(linguisticSummaryGenerator.qualifier));
        results.add(t10(linguisticSummaryGenerator.qualifier));
        results.add(t11(linguisticSummaryGenerator.qualifier));

        return results;
    }

    double t(List<Double> weights, LinguisticSummaryGenerator linguisticSummaryGenerator, QuantifierLabel qualifierLabel, int allBlockGroups) {
        List<Double> results = all_t(linguisticSummaryGenerator, qualifierLabel, allBlockGroups);
        double sum = 0.0;
        for (int i = 0; i < results.size(); i++) {
            sum += weights.get(i) * results.get(i);
        }
        return sum;
    }

}

