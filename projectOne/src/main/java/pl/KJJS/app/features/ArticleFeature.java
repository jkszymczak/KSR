package pl.KJJS.app.features;

import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Keys;
import pl.KJJS.app.parser.Reader;

import java.io.IOException;
import java.util.HashMap;

public class ArticleFeature {
    ECountries country;
    FeatureVector featureVector;

    public ArticleFeature(Article article, HashMap<Keys, HashMap<ECountries, String[][]>> dicts) {
        this.featureVector = new FeatureVector();
        this.country = article.getPlace();
        this.featureVector.calculateFeatures(dicts, article.getBody());
    }

    public ECountries getCountry() {
        return country;
    }

    public FeatureVector getFeatureVector() {
        return featureVector;
    }
}
