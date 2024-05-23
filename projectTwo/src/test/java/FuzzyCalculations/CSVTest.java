package FuzzyCalculations;

import Database.BlockGroup;
import Database.CSV;
import junit.framework.TestCase;

import java.util.List;

public class CSVTest extends TestCase {

    public void testReadCSV() {
        String path = "dataBasePrep/prepared.csv";
        List<BlockGroup> result = CSV.readCSV(path);
        System.out.println("Length: " + result.size());
        System.out.println("first record: " + result.get(0).toString());
    }
}