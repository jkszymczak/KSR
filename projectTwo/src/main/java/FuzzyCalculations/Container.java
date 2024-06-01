package FuzzyCalculations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import FuzzyCalculations.SummarizerQualifier;

public class Container {
    Map<String, SummarizerQualifier> labels;

    public Container(List<SummarizerQualifier> labels) {
        this.labels = labels.stream().collect(Collectors.toMap(k -> k.getLabel(), v -> v));
    }

    public SummarizerQualifier getLabel(String label) {
        if (labels.containsKey(label)) return labels.get(label);
        throw new RuntimeException("There is no summarizer or qualifier with label " + label);
    }

    public Map<String, SummarizerQualifier> getLabels() {
        return this.labels;
    }
}
