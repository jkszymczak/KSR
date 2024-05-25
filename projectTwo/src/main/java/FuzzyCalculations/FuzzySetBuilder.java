package FuzzyCalculations;

import Builders.MembershipFunctionBuilder;
import Builders.SummarizerQualifierBuilder;
import Database.BlockGroup;

import java.util.List;

public class FuzzySetBuilder implements SetBuilder<SummarizerQualifierBuilder,FuzzySetBuilder,FuzzySet> {
    private SummarizerQualifierBuilder upper;
    private String label;
    private MembershipFunction membershipFunction;
    private List<BlockGroup> candidates;
    private Columns column;

    public FuzzySetBuilder(SummarizerQualifierBuilder upper){
        this.upper = upper;
    }

    public FuzzySetBuilder(){

    }

    public static FuzzySetBuilder builder(){
        return new FuzzySetBuilder();
    }


    public FuzzySetBuilder withLabel(String label){
        this.label = label;
        return this;
    }

    @Override
    public FuzzySetBuilder withMembershipFuntion(MembershipFunction membershipFunction){
        this.membershipFunction = membershipFunction;
        return this;
    }
    public MembershipFunctionBuilder<FuzzySetBuilder> createMembershipFunction(){
        return new MembershipFunctionBuilder<FuzzySetBuilder>(this);
    }

    public FuzzySetBuilder onColumn(Columns column){
        this.column = column;
        return this;
    }

    public FuzzySetBuilder withCandidates(List<BlockGroup> candidates){
        this.candidates = candidates;
        return this;
    }

    @Override
    public FuzzySet end(){
        return new FuzzySet(candidates,membershipFunction,label,column);
    }

    @Override
    public SummarizerQualifierBuilder build(){
        FuzzySet built = this.end();
        return upper.withFuzzySet(built);
    }

}
