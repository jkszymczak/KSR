package pl.KJJS.app.knn;

import pl.KJJS.app.features.ArticleFeature;
import pl.KJJS.app.features.ECountries;
import pl.KJJS.app.features.FeatureVector;
import pl.KJJS.app.metrics.Metric;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.quality.Measures;

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
                .sorted(Map.Entry.<ArticleFeature,Double>comparingByValue().reversed())
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
}
