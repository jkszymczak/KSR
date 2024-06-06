package FuzzyCalculations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import FuzzyCalculations.SummarizerQualifier;

public class Container {
    Map<String, SummarizerQualifier> labels;
    private Container original = null;

    public Container(List<SummarizerQualifier> labels) {
        this.labels = labels.stream().collect(Collectors.toMap(k -> k.getLabel(), v -> v));
    }
    public Container(Container c){
        this.labels = new HashMap<>(c.getLabels());
    }

    public SummarizerQualifier getLabel(String label) {
        if (labels.containsKey(label)) return labels.get(label);
        throw new RuntimeException("There is no summarizer or qualifier with label " + label);
    }

    public void addLabel(SummarizerQualifier label){
        if (original == null){
            this.original = new Container(this);
        }
        this.labels.put(label.getLabel(),label);
    }

    public Boolean reset(){
        if(this.original == null) return false;
        this.labels = this.original.labels;
        this.original = null;
        return true;
    }



    public Map<String, SummarizerQualifier> getLabels() {
        return this.labels;
    }
}
