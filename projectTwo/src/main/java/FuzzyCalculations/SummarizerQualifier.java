package FuzzyCalculations;

import java.util.List;

public class SummariserQualifier {
    Columns column;
    FuzzySet fuzzySet;
    List<SummariserQualifier> elementalParts = null;
    public SummariserQualifier and(SummariserQualifier sum2){
        return null;
    };

    public SummariserQualifier setColumn(Columns column) {
        this.column = column;
        return this;
    }
    public SummariserQualifier setFuzzySet(FuzzySet fuzzySet) {
        this.fuzzySet = fuzzySet;
        return this;
    }

}
