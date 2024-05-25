package Builders;

import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.QuantifierType;
import org.example.Pair;

import java.util.LinkedList;
import java.util.List;

public class FuzzyQuantifierBuilder implements Builder<LinguisticSummaryBuilder, FuzzyQuantifier> {
    private List<QuantifierLabel> labels = new LinkedList<>();
    private QuantifierType type;
    private LinguisticSummaryBuilder upper;
    private Pair<Double,Double> range;

    public static FuzzyQuantifierBuilder builder() {
        return new FuzzyQuantifierBuilder();
    }

    public FuzzyQuantifierBuilder(LinguisticSummaryBuilder upper){
        this.upper = upper;
    }
    public FuzzyQuantifierBuilder(){
    }

    public FuzzyQuantifierBuilder addLabels(List<QuantifierLabel> labels) {
        this.labels.addAll(labels);
        return this;
    }

    public FuzzyQuantifierBuilder withType(QuantifierType type) {
        this.type = type;
        return this;
    }

    public FuzzyQuantifierBuilder onRange(double start,double stop){
        this.range = new Pair<>(start,stop);
        return this;
    }

    public LinguisticSummaryBuilder build() {
        FuzzyQuantifier built = this.end();
        return upper.withQuantifier(built);
    }
    public FuzzyQuantifier end(){
        for (QuantifierLabel label : this.labels){
            label.getMembershipFunction().setRange(this.range);
        }
        return new FuzzyQuantifier(this.labels,this.type);
    }
    public FuzzyQuantifierBuilder addLabel(QuantifierLabel label) {
        this.labels.add(label);
        return this;
    }
    public QuantifierLabelBuilder createLabel(){
        return new QuantifierLabelBuilder(this);
    }
}
