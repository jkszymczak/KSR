package FuzzyCalculations;

import java.util.List;

public class SummarizerBuilder extends SummarizerQualifier{
    MembershipFunction membershipFunction;
    List<Member> elements;
    String label;

    public SummarizerBuilder setColumn(Columns column) {
//        this.column = column;
        return this;
    }
    public SummarizerBuilder setFuzzySet(FuzzySet fuzzySet) {
        this.fuzzySet = fuzzySet;
        return this;
    }
//    public SummarizerBuilder buildSet()
}
