package Builders;

import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.QuantifierType;

import java.util.LinkedList;
import java.util.List;

public class FuzzyQuantifierBuilder implements Builder<LinguisticSummaryBuilder, FuzzyQuantifier> {
    private List<QuantifierLabel> labels = new LinkedList<>();
    private QuantifierType type;
    private LinguisticSummaryBuilder upper;

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

    public LinguisticSummaryBuilder build() {
        FuzzyQuantifier built = end();
        return upper.withQuantifier(built);
    }
    public FuzzyQuantifier end(){
        return new FuzzyQuantifier(labels,type);
    }
    public FuzzyQuantifierBuilder addLabel(QuantifierLabel label) {
        this.labels.add(label);
        return this;
    }
    public QuantifierLabelBuilder createLabel(){
        return new QuantifierLabelBuilder(this);
    }
}
