package Database;

import LinguisticSummarization.LinguisticSummary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
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
    private static List<String> getHeaders(){
        String headers[] = {"Summary","t1","t2","t3","t4","t5","t6","t7","t8","t9","t10","t11","T"};
        return List.of(headers);
    }
    public static void saveSummariesCSV(String path, List<LinguisticSummary> summaries) throws IOException {
        StringWriter sw = new StringWriter();
        List<String> headers = getHeaders();
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers.toArray(new String[0])).build();
        CSVPrinter csvPrinter = new CSVPrinter(sw,csvFormat);
        summaries.forEach(summarie -> {
            try {
                csvPrinter.print(summarie.getLabel());
                for (int i = 0;i<summarie.getQualityMeasures().size();i++){
                    csvPrinter.print(summarie.getQualityMeasures().get(i));
                }
//                csvPrinter.print();
                csvPrinter.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        FileWriter results = new FileWriter(path);
        results.write(sw.toString());
        results.close();


    }

}
