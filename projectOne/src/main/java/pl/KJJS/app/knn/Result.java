package pl.KJJS.app.knn;

import pl.KJJS.app.features.ArticleFeature;
import pl.KJJS.app.features.ECountries;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.quality.Label;

public class Result implements Label {
    ECountries result;
    ECountries expected;
    ArticleFeature article;

    public Result(ArticleFeature article,ECountries result){
        this.article = article;
        this.expected = article.getCountry();
        this.result = result;
    }

    @Override
    public ECountries getResult() {
        return this.result;
    }

    @Override
    public ECountries getExpected() {
        return this.expected;
    }
}
