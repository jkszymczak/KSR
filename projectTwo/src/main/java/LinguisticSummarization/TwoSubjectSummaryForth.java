package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.Member;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TwoSubjectSummaryForth {
    Subject first;
    Subject second;
    List<BlockGroup> firstSubject;
    List<BlockGroup> secondSubject;
    SummarizerQualifier summarizerFirst;
    SummarizerQualifier summarizerSecond;
    Double degreeOfTruth;
    String label = "More";
    String summarizerConjunction;

    public TwoSubjectSummaryForth(Subject first, Subject second, SummarizerQualifier summarizer, List<BlockGroup> all, String summarizerConjunction) {
        this.first = first;
        this.second = second;
        splitToSubjects(all);
        System.out.println("Summarizer elements" + summarizer.getElements().size());
        this.summarizerFirst = summarizer.filterSummarizer(this.first.label);
        System.out.println("Summarizer elements" + summarizer.getElements().size());
        this.summarizerSecond = summarizer.filterSummarizer(this.second.label);
        System.out.println("Number of elements in first subject: " + summarizerFirst.getElements().size());
        System.out.println("Number of elements in second subject: " + summarizerSecond.getElements().size());
        this.summarizerConjunction = summarizerConjunction;
        this.degreeOfTruth = calculateDegreeOfTruth();
    }

    private void splitToSubjects(List<BlockGroup> all) {
        this.firstSubject = all.stream().filter(blockGroup -> blockGroup.getLabel().equals(first.label)).toList();
        this.secondSubject = all.stream().filter(blockGroup -> blockGroup.getLabel().equals(second.label)).toList();
    }

    private Double calculateDegreeOfTruth() {
//        this.degreeOfTruth = 0.0;
        Double f = (!summarizerFirst.getElements().isEmpty())? summarizerFirst.cardinal() / summarizerFirst.getElements().size() : 0;
        Double s = (!summarizerSecond.getElements().isEmpty())? summarizerSecond.cardinal() / summarizerSecond.getElements().size() : 0;

        Double val =  raisenbach(f,s);

        return 1 - val;
    }

    private double raisenbach(Double a, Double b) {
        return 1 - a + a * b;
    }

    public List<Pair<String, Double>> generateSummaries() {
        List<Pair<String, Double>> result = new LinkedList<>();
        String first = this.label + " Block Groups " + this.first.label + " than Block Groups " + this.second.label + " " + this.summarizerConjunction + " " + this.summarizerFirst.getLabel();
        result.add(new Pair<>(first, this.degreeOfTruth));
        return result;
    }
}
