package Builders;

import FuzzyCalculations.MembershipFunction;
import FuzzyCalculations.QuantifierLabel;
import FuzzyCalculations.SetBuilder;

public class QuantifierLabelBuilder implements SetBuilder<FuzzyQuantifierBuilder,QuantifierLabelBuilder, QuantifierLabel> {
    FuzzyQuantifierBuilder upper;
    MembershipFunction membershipFunction;
    String label;

    public QuantifierLabelBuilder(FuzzyQuantifierBuilder upper) {
        this.upper = upper;
    }
    public QuantifierLabelBuilder() {
        this.upper = upper;
    }


    public static QuantifierLabelBuilder builder(){
        return new QuantifierLabelBuilder();
    }

    @Override
    public FuzzyQuantifierBuilder build() {
        QuantifierLabel built = this.end();
        return upper.addLabel(built);
    }

    @Override
    public QuantifierLabel end() {
        return new QuantifierLabel(membershipFunction,label);
    }

    @Override
    public QuantifierLabelBuilder withMembershipFuntion(MembershipFunction MembershipFunction) {
        this.membershipFunction = MembershipFunction;
        return this;
    }
    public MembershipFunctionBuilder<QuantifierLabelBuilder> createMembershipFunction(){
        return new MembershipFunctionBuilder<QuantifierLabelBuilder>(this);
    }

    public QuantifierLabelBuilder withLabel(String label) {
        this.label=label;
        return this;
    }
}
