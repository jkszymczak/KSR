package FuzzyCalculations;

import org.example.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SummarizerQualifier {
//    Columns column;
    FuzzySet fuzzySet;
    List<SummarizerQualifier> elementalParts;
    String conjunction = "AND";


    public SummarizerQualifier(FuzzySet fuzzySet) {
        this.fuzzySet = fuzzySet;
        this.elementalParts = new LinkedList<>();
        this.elementalParts.add(this);
    }


    public SummarizerQualifier and(SummarizerQualifier sum2){
        FuzzySet first = this.fuzzySet;
        FuzzySet second = sum2.fuzzySet;

        return new SummarizerQualifier(this, sum2,first.and(second));
    };

    private SummarizerQualifier(SummarizerQualifier sum1, SummarizerQualifier sum2,FuzzySet fuzzySet) {
        this.elementalParts = new LinkedList<>();

        if (sum1.isComplex()) this.elementalParts.addAll(sum1.elementalParts);
        else this.elementalParts.add(sum1);

        if (sum2.isComplex()) this.elementalParts.addAll(sum2.elementalParts);
        else this.elementalParts.add(sum2);

        this.fuzzySet = fuzzySet;
    }

    private void setElementalParts(SummarizerQualifier part){
        if(this.elementalParts == null){
            this.elementalParts = new LinkedList<>();
        }
        this.elementalParts.add(part);
    }

    public Columns getColumn(){
        return this.fuzzySet.getColumn();
    }
    public String getLabel(){
        return this.fuzzySet.getLabel();
    }
    public Map<Integer,Member> getElements(){
        return this.fuzzySet.getElements();
    }
    public double cardinal(){
        return this.fuzzySet.cardinal();
    }
    public Pair<Double,Double> getRange(){
        return this.fuzzySet.getMembershipFunction().getRange();
    }
    public Pair<Double, Double> getSupport() {
        return this.fuzzySet.getMembershipFunction().getSupport();
    }

    public List<SummarizerQualifier> getElementalParts(){
        return this.elementalParts;
    }

    private Boolean isComplex(){
        if(this.elementalParts != null) return true;
        return false;
    }

}
