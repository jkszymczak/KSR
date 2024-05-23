package Database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockGroup {
    Map<String,Double> columns;
    String label = null;
    int index;

    public BlockGroup(Map<String,Double> columns,String label) {
        this.columns = columns;
        this.label = label;
    }
    public BlockGroup(Map<String, Object> columns) {
        this.columns = new HashMap<>();
        columns.forEach((k,v)->{
            if(k=="index") {
                this.index = (int)v;
            } else if(v.getClass() == String.class) {
                this.label = (String) v;
            } else {
                this.columns.put(k, (Double) v);
            }
        });
        this.columns.remove("index");
    }

    public Map<String, Double> getColumns() {
        return columns;
    }
    public int getIndex() {
        return index;
    }

    public String toString(){
        return "Index: "+ this.index + ", value" + columns.toString() + "Label: " + label;
    }
}
