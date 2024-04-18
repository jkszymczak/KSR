package pl.KJJS.app;

import junit.framework.TestCase;
import pl.KJJS.app.features.ArticleFeature;
import pl.KJJS.app.features.ECountries;
import pl.KJJS.app.features.IleFeatures;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Keys;
import pl.KJJS.app.parser.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FeatureTest extends TestCase {
    public void testSimpleIleFeature() throws IOException {
        System.out.println("Hello World!");
        Reader r = new Reader();
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts();
        List<Article> articles = Reader.readArticles("input").stream().limit(300).toList();
        int index = 3;
//        List<ArticleFeature> vectors = new ArrayList<>();
//
//        for (Article article : articles) {
//            vectors.add(new ArticleFeature(article,dicts));
//        }
//        List<ArticleFeature> learnSet = vectors.subList(0,100);
//        System.out.println(learnSet.size());
//        List<ArticleFeature> testSet = vectors.subList(100,300);
//        System.out.println(testSet.size());

        // =================================================================
        IleFeatures ileFeatures = new IleFeatures();
        System.out.println(IleFeatures.getFeaturesNumbers());
        ileFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>() {{
            add(Keys.characteristic_words);
            // add dict to continents
        }}, articles.get(index).getBody());

        System.out.println(Arrays.toString(ileFeatures.getFeaturesAsVector()));
        // =================================================================

        System.out.println("Hello End");
    }

    public void testComplexIleFeature() throws IOException {
        System.out.println("Hello World!");
        Reader r = new Reader();
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts();
        List<Article> articles = Reader.readArticles("input").stream().limit(300).toList();
        int index = 3;

        ArticleFeature articleFeature = new ArticleFeature(articles.get(index), dicts);

        System.out.println(Arrays.deepToString(articleFeature.getFeatureVector().getNumericFeatures()));
        System.out.println(Arrays.deepToString(articleFeature.getFeatureVector().getLogicFeatures()));
        System.out.println(Arrays.toString(articleFeature.getFeatureVector().getTextFeatures()));

        System.out.println("Hello End");
    }
}
