package FuzzyCalculations;

import Database.BlockGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummarizerQualifier {
//    Columns column;
    FuzzySet fuzzySet;
    List<SummarizerQualifier> elementalParts = null;
    public SummarizerQualifier and(SummarizerQualifier sum2){
        FuzzySet first = this.fuzzySet;
        FuzzySet second = sum2.fuzzySet;

        return new SummarizerQualifier(this, sum2,first.and(second));
    };

    private SummarizerQualifier(SummarizerQualifier sum1, SummarizerQualifier sum2,FuzzySet fuzzySet) {
        this.elementalParts = new LinkedList<>();
        this.elementalParts.add(sum1);
        this.elementalParts.add(sum2);
        this.fuzzySet = fuzzySet;
    }

    private void setElementalParts(SummarizerQualifier part){
        if(this.elementalParts == null){
            this.elementalParts = new LinkedList<>();
        }
        this.elementalParts.add(part);
    }
    public static SummarizerBuilder build(){
        return new SummarizerBuilder();
    }
    public Columns getColumn(){
        return this.fuzzySet.getColumn();
    }
    public String getLabel(){
        return this.fuzzySet.getLabel();
    }

}
