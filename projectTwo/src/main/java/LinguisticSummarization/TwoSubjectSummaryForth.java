package LinguisticSummarization;

import Database.BlockGroup;
import FuzzyCalculations.SummarizerQualifier;
import org.example.Pair;

import java.util.LinkedList;
import java.util.List;

public class TwoSubjectSummaryForth {
    Subject first;
    Subject second;
    List<BlockGroup> firstSubject;
    List<BlockGroup> secondSubject;
    SummarizerQualifier summarizerFirst;
    SummarizerQualifier summarizerSecond;
    Double degreeOfTruth;
    String label = "More";

    public TwoSubjectSummaryForth(Subject first,Subject second,SummarizerQualifier summarizer,List<BlockGroup> all){
        this.first=first;
        this.second=second;
        splitToSubjects(all);
        this.summarizerFirst = summarizer.filterSummarizer(this.first.label);
        this.summarizerSecond = summarizer.filterSummarizer(this.second.label);
        this.degreeOfTruth = calculateDegreeOfTruth();
    }

    private void splitToSubjects(List<BlockGroup> all){
        this.firstSubject = all.stream().filter(blockGroup -> blockGroup.getLabel().equals(first.label)).toList();
        this.secondSubject = all.stream().filter(blockGroup -> blockGroup.getLabel().equals(second.label)).toList();
    }
    private Double calculateDegreeOfTruth(){
//        this.degreeOfTruth = 0.0;
        return 0.0;
    }
    public List<Pair<String,Double>> generateSummaries(){
        List<Pair<String,Double>> result = new LinkedList<>();
        String first = this.label+" Block Groups "+this.first.label+" than Block Groups "+this.second.label+" are "+this.summarizerFirst.getLabel();
        result.add(new Pair<>(first,this.degreeOfTruth));
        return result;
    }
}
