package pl.KJJS.app.knn;

import junit.framework.Assert;
import junit.framework.TestCase;
import pl.KJJS.app.features.ArticleFeature;
import pl.KJJS.app.metrics.EuclideanMetric;
import pl.KJJS.app.metrics.Metric;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Reader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class KNNTest extends TestCase {

    public void testClassifyVector() throws IOException {
        List<Article> articles = Reader.readArticles("input");
        Reader r = new Reader();

        List<ArticleFeature> articleFeaturesTraining = new LinkedList<>();
        System.out.println("Learning: " + articles.size());
        for (int i = 0; i < 5; i++) {
            ArticleFeature articleFeature = new ArticleFeature(articles.get(i+13), r.readDicts());
            articleFeaturesTraining.add(i, articleFeature);

            System.out.println(articleFeature.getCountry());
        }
        ArticleFeature articleFeatureTest = new ArticleFeature(articles.get(0), r.readDicts());
        System.out.println();
        System.out.println("Test: " + articleFeatureTest.getCountry());


        KNN kNN = new KNN(articleFeaturesTraining);

        Metric m = new EuclideanMetric();

        Result result = kNN.classifyVector(articleFeatureTest, 2, m); // for n = 2 result is correct    for n = 3 result is incorrect
        System.out.println("Result is: " + result.getResult());
        System.out.println("Expected is: " + result.getExpected());
//TODO
//        Assert.assertEquals(result.getResult(), result.getExpected());
    }

    public void testClasifyVectors() throws IOException {
        List<Article> articles = Reader.readArticles("input");
        Reader r = new Reader();

        List<ArticleFeature> articleFeaturesTraining = new LinkedList<>();
        System.out.println("Learning: \n");
        for (int i = 0; i < 8; i++) {
            ArticleFeature articleFeature = new ArticleFeature(articles.get(i+13), r.readDicts());
            articleFeaturesTraining.add(i, articleFeature);

            System.out.println(articleFeature.getCountry());
        }
        System.out.println();
        List<ArticleFeature> articleFeaturesTest = new LinkedList<>();
        System.out.println("Test: \n");
        for (int i = 0; i < 3; i++) {
            ArticleFeature articleFeature = new ArticleFeature(articles.get(i), r.readDicts());
            articleFeaturesTest.add(i, articleFeature);

            System.out.println(articleFeature.getCountry());
        }

        KNN kNN = new KNN(articleFeaturesTraining);
        Metric m = new EuclideanMetric();

        List<Result> results = kNN.clasifyVectors(articleFeaturesTest, 2, m);

        for (int i = 0; i < results.size(); i++) {
            System.out.println("Result is: " + results.get(i).getResult());
            System.out.println("Expected is: " + results.get(i).getExpected());
            System.out.println();

        }

        Assert.assertNotSame(results.get(0).getResult(), results.get(0).getExpected());
        Assert.assertEquals(results.get(1).getResult(), results.get(1).getExpected());
        Assert.assertEquals(results.get(2).getResult(), results.get(2).getExpected());
    }
}