package FuzzyCalculations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Container {
    Map<String,SummarizerQualifier> labels;
    public Container(List<SummarizerQualifier> labels){
        this.labels = labels.stream().collect(Collectors.toMap(k -> k.getLabel(),v -> v));
    }
    public SummarizerQualifier getLabel(String label){
        return labels.get(label);
    }
}
