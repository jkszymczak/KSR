package pl.KJJS.app;

import junit.framework.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import junit.framework.TestCase;
import pl.KJJS.app.features.ArticleFeature;
import pl.KJJS.app.features.ECountries;
import pl.KJJS.app.features.HowManyFeatures;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Keys;
import pl.KJJS.app.parser.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FeatureTest extends TestCase {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testIleFeature(int index) throws IOException {
        System.out.println("Hello World!");
        Reader r = new Reader();
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts();
        List<Article> articles = Reader.readArticles("input").stream().limit(300).toList();
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
        HowManyFeatures howManyFeatures = new HowManyFeatures();
        System.out.println(HowManyFeatures.getFeaturesNumbers());
        howManyFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>() {{
            add(Keys.characteristic_words);
            // add dict to continents
        }}, articles.get(index).getBody());

        System.out.println(Arrays.toString(howManyFeatures.getFeaturesAsVector()));
        // =================================================================

        System.out.println("Hello End");
        Assert.assertTrue(true);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 10, 100, 111, 122, 130, 150, 200})
    public void testAllFeature(int index) throws IOException {
        System.out.println("Hello World!");
        Reader r = new Reader();
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts();
        List<Article> articles = Reader.readArticles("input").stream().limit(300).toList();

        ArticleFeature articleFeature = new ArticleFeature(articles.get(index), dicts);

        System.out.println(Arrays.deepToString(articleFeature.getFeatureVector().getNumericFeatures()));
        System.out.println(Arrays.deepToString(articleFeature.getFeatureVector().getLogicFeatures()));
        System.out.println(Arrays.toString(articleFeature.getFeatureVector().getTextFeatures()));

        System.out.println("Hello End");
        Assert.assertTrue(true);
    }
}
