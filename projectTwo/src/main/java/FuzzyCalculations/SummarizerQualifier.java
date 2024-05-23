package FuzzyCalculations;

import Database.BlockGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SummarizerQualifier {
//    Columns column;
    FuzzySet fuzzySet;
    List<SummarizerQualifier> elementalParts = null;
    public SummarizerQualifier and(SummarizerQualifier sum2){

        return null;
    };
    private void setElementalParts(SummarizerQualifier part){
        if(this.elementalParts == null){
            this.elementalParts = new LinkedList<>();
        }
        this.elementalParts.add(part);
    }
    public static SummarizerBuilder build(){
        return new SummarizerBuilder();
    }

}
