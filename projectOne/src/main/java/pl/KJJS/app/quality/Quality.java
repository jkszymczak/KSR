package pl.KJJS.app.quality;

import pl.KJJS.app.features.ECountries;
import pl.KJJS.app.knn.Result;

import java.util.HashMap;
import java.util.List;

public class Quality {
    public static Double calculateAccuracy(List<Result> articles){
        int accumulator = 0;
        for(Label a:articles){
            if(a.getExpected()==a.getResult()) accumulator++;
        }
        return (double) (accumulator/articles.size());
    }
    public static Double calculatePrecision(List<Result> articles, ECountries c){
        Double all = 0.0;
        Double pk =0.0;
        for(Label a:articles){
            if(a.getResult()==c) {
                all++;
                if(a.getResult()==a.getExpected()) pk++;
            }
        }
        if(all == 0.0) return 0.0;
        return pk/all;
    }
    public static Double calculateRecall(List<Result> articles,ECountries c){
        Double all = 0.0;
        Double pk =0.0;
        for(Label a:articles){
            if(a.getExpected()==c) {
                all++;
                if(a.getExpected()==a.getResult()) pk++;
            }
        }
        if(all == 0.0) return 0.0;
        return pk/all;
    }
    public static Double calculateF(List<Result>articles, ECountries c){
        Double acc = Quality.calculateAccuracy(articles);
        Double rec = Quality.calculateRecall(articles,c);
        if(acc == 0.0 && rec == 0.0) return 0.0;
      return 2*(acc*rec)/(acc+rec);
    }
    public static Double calculateAgregation(List<Result> articles,Measures m){
        HashMap<ECountries,Double> weights = new HashMap<>();
        for (ECountries c:ECountries.values()){
            weights.put(c,0.0);
        }

        for (Label a:articles){
            weights.computeIfPresent(a.getExpected(),(k,v) -> v+1);
        }
        switch (m){
            case f1 -> {
                Double acc=0.0;
                for (ECountries c:ECountries.values()){
                    acc+=(Quality.calculateF(articles,c)*weights.get(c));
                }
                return acc/articles.size();
            }
            case precision -> {
                Double acc=0.0;
                for (ECountries c:ECountries.values()){
                    acc+=(Quality.calculatePrecision(articles,c)*weights.get(c));
                }
                return acc/articles.size();
            }
            case recall -> {
                Double acc=0.0;
                for (ECountries c:ECountries.values()){
                    acc+=(Quality.calculateRecall(articles,c)*weights.get(c));
                }
                return acc/articles.size();
            }
            case accuracy -> {
                return Quality.calculateAccuracy(articles);
            }
        }
        return null;
    }
    public static HashMap<Measures,Double> calculateAllAg(List<Result> articles){
        HashMap<Measures,Double> result = new HashMap<>();
        for (Measures m:Measures.values()){
            result.put(m,calculateAgregation(articles,m));
        }
        return result;
    }


}
