package FuzzyCalculations;

import org.example.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class FuzzyQuantifier {
    List<QuantifierLabel> labels;
    QuantifierType type;

    public FuzzyQuantifier(List<QuantifierLabel> labels, QuantifierType type) {
        this.labels = labels;
        this.type = type;
    }

    public List<Pair<String,Double>> calculateMemberships(double value){
        return labels.stream().map(
                label -> new Pair<>(label.getLabel()
                        ,label.evaluate(value))
        ).collect(Collectors.toList());
    }
}
