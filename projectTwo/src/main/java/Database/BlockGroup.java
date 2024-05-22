package FuzzyCalculations;

import java.util.Map;

public class BlockGroup {
    Map<String,Double> columns;
    public BlockGroup(Map<String,Double> columns) {
        this.columns = columns;
    }
    public String toString(){
        return columns.toString();
    }
}
