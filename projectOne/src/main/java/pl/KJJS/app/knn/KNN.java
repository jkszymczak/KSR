package pl.KJJS.app.knn;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import pl.KJJS.app.features.ArticleFeature;
import pl.KJJS.app.features.ECountries;
import pl.KJJS.app.metrics.Metric;
import pl.KJJS.app.quality.Measures;
import pl.KJJS.app.quality.Quality;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

public class KNN {

    List<ArticleFeature> learningSet;

    public KNN (List<ArticleFeature> learningSet) {
        this.learningSet = learningSet;

    }

    public Result classifyVector(ArticleFeature articleFeature, int neighbours, Metric m) {
        HashMap<ArticleFeature, Double> results = new HashMap<>();
        for (ArticleFeature feature : learningSet) {
            results.put(feature, m.calculateMetric(feature.getFeatureVector(), articleFeature.getFeatureVector()));
        }
        Map<ArticleFeature, Double> nBest = results
                .entrySet().stream()
                .sorted(Map.Entry.<ArticleFeature,Double>comparingByValue())
                .limit(neighbours).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        HashMap<ECountries, Integer> labels = new HashMap<>();
        for (ECountries country : ECountries.values()) {
            labels.put(country, 0);
        }
        for (Map.Entry<ArticleFeature, Double> entry : nBest.entrySet()) {
            labels.computeIfPresent(entry.getKey().getCountry(), (k, v) -> v + 1);
        }

        int max = Collections.max(labels.values());
        ECountries classifiedLabel = labels.entrySet().stream().filter(l -> l.getValue() == max).limit(1).map(Map.Entry::getKey).collect(Collectors.toList()).get(0);

        return new Result(articleFeature, classifiedLabel);
    }

    public List<Result> clasifyVectors(List<ArticleFeature> featureVectors, int neighbours, Metric m) {
        List<Result> results = new LinkedList<>();
        for (int i = 0; i < featureVectors.size(); i++) {
            results.add(i, classifyVector(featureVectors.get(i), neighbours, m));
        }
        return results;
    }

    public int countCountryExpected(List<Result> results,ECountries country) {
        int count = 0;
        for (Result result : results) {
            if(result.getExpected()==country) count++;
        }
        return count;
    }
    public int countCountryResult(List<Result> results,ECountries country) {
        int count = 0;
        for (Result result : results) {
            if(result.getResult()==country) count++;
        }
        return count;
    }

    List<String> prepareHeaders(){
        List<String> headers = new ArrayList<>();
        headers.add("K");
        for (ECountries c: ECountries.values()) {
            headers.add(c.toString()+"_learning_set");
            headers.add(c.toString()+"_expected");
            headers.add(c.toString()+"_result");
        }
        for (ECountries c: ECountries.values()) {
            for (Measures measures:Measures.values()){
                if(measures == Measures.accuracy) continue;
                headers.add(c.toString()+"_"+measures.name());
            }
        }
        for (Measures measure : Measures.values()) {
            headers.add(measure.name());
        }
        return headers;
    }
    void addToCSV(CSVPrinter csvPrinter,int i ,List<Result> results, HashMap<Measures,Double> measures) throws IOException {
        HashMap<ECountries,Integer> learningCount = new HashMap<>();
        for (ECountries c: ECountries.values()) {
            int count = this.learningSet.stream().filter(a -> a.getCountry()==c).toList().size();
            learningCount.put(c,count);

        }
        // put element on line
        csvPrinter.print(i);
        for (ECountries c: ECountries.values()) {
            csvPrinter.print(learningCount.get(c));
            csvPrinter.print(this.countCountryExpected(results,c));
            csvPrinter.print(this.countCountryResult(results,c));

        }
        for (ECountries c: ECountries.values()) {
            csvPrinter.print(Quality.calculateRecall(results,c));
            csvPrinter.print(Quality.calculatePrecision(results,c));
            csvPrinter.print(Quality.calculateF(results,c));
//                Quality.calculatePrecision(results,c);
        }
        measures.forEach((k,v) -> {
            try {
                csvPrinter.print(v);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // insert endline
        csvPrinter.println();
        System.out.print("\t DONE \n");
    }

    /**
     * This method runs knn of each n given in array,
     * then runs quality measures for every result and saves to csv with N.
     * IMPORTANT!!! Every run overwrites file AND there is NOTHING that saves what METRIC was used
     * */
    public void rateToFile(List<ArticleFeature> vectors,Metric m,int[] n,String filename) throws IOException {
        StringWriter sw = new StringWriter();
        List<String> headers = prepareHeaders();
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers.toArray(new String[0])).build();
        CSVPrinter csvPrinter = new CSVPrinter(sw,csvFormat);


        for(int i:n){
            System.out.print("Starting kNN for k="+i+"...");
            List<Result> results = this.clasifyVectors(vectors,i,m);
            HashMap<Measures,Double> measures = Quality.calculateAllAg(results);
            System.out.print("\t DONE \n");
            System.out.print("Adding to "+filename+"...");
            addToCSV(csvPrinter,i,results,measures);

        }
        System.out.print("Saving file "+filename+"...");
        FileWriter resultsFile = new FileWriter(filename);
        resultsFile.write(sw.toString());
        resultsFile.close();
        System.out.print("\t DONE \n");
    }
}
