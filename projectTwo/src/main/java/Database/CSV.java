package Database;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CSV {
    public static List<BlockGroup> readCSV(String path){
        List<Map<String,Object>> records = new LinkedList<>();
        try (FileReader fileReader = new FileReader(path);
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build())){
            for (CSVRecord csvRecord : csvParser){
                Map<String, Object> rowMap = new HashMap<>();
                csvRecord.toMap().forEach((key, value) -> {
                    try {
                        rowMap.put(key, Double.parseDouble(value));
                    } catch (NumberFormatException e) {
                        rowMap.put(key, value); // Handle non-numeric values appropriately
                    }
                });
                records.add(rowMap);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records.stream().map(BlockGroup::new).collect(Collectors.toList());
    }
}
