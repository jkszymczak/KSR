package pl.KJJS.app;

import pl.KJJS.app.features.*;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Keys;
import pl.KJJS.app.parser.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );

        List<Article> articles = Reader.readArticles("input");
        Reader r = new Reader();
//        System.out.println(Reader.readArticles("input").size());
//        Reader r = new Reader();
//        r.readDicts();
//        System.out.println(r.readStopList());

        System.out.println("Number of articles: " + articles.size());
        System.out.println("Read done!");
        int index = 0;

        System.out.println(Arrays.toString(Stream.of(articles.get(index).getBody()).toArray()));

// =================================================================
//        LiczFeatures liczFeatures = new LiczFeatures();
//        // !!! Keys must be the same order as the ECoreFeature enum !!!
//        liczFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>(){{
//            add(Keys.geographic_locations);
//            add(Keys.architectural_objects);
//            add(Keys.cities);
//            add(Keys.fameous_people);
//        }}, articles.get(index).getBody());
//
//        System.out.println(Arrays.toString(liczFeatures.getFeaturesAsVector()));
// =================================================================

// =================================================================
//        IleFeatures ileFeatures = new IleFeatures();
//        System.out.println(IleFeatures.getFeaturesNumbers());
//        ileFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>(){{
//            add(Keys.characteristic_words);
//            // add dict to continents
//        }}, articles.get(index).getBody());
//
//        System.out.println(Arrays.toString(ileFeatures.getFeaturesAsVector()));
// =================================================================

// =================================================================
//
//        CzyFeatures czyFeatures = new CzyFeatures();
//        System.out.println(CzyFeatures.getFeaturesNumbers());
//        czyFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>(){{
//            add(Keys.institutions);
//            add(Keys.cities);
//            add(Keys.dates);
//        }}, articles.get(index).getBody());
//        System.out.println(Arrays.deepToString(czyFeatures.getFeaturesAsVector()));
//
// =================================================================

// =================================================================

//        TekstFeatures tekstFeatures = new TekstFeatures();
//        System.out.println(TekstFeatures.getFeaturesNumbers());
//        tekstFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>(){{
//            add(Keys.cities);
//        }}, articles.get(index).getBody());
//
//        System.out.println(Arrays.toString(tekstFeatures.getFeaturesAsVector()));

// =================================================================


// =================================================================

//        FeatureVector featureVector = new FeatureVector();
//        featureVector.calculateFeatures(r.readDicts(), articles.get(index).getBody());
//        System.out.println(Arrays.toString(featureVector.getNumericFeatures()));
//        System.out.println(Arrays.deepToString(featureVector.getLogicFeatures()));
//        System.out.println(Arrays.toString(featureVector.getTextFeatures()));

// =================================================================

// =================================================================

        //TODO próbuję zrobić listę featureVectors pomóż
//        List<FeatureVector> featureVectors = new ArrayList<FeatureVector>();
//        for (Article article : articles) {
//            FeatureVector featureVector = new FeatureVector();
//            featureVector.calculateFeatures(r.readDicts(), article.getBody());
//            featureVectors.add(featureVector);
//        }

        FeatureVector featureVector = new FeatureVector();
        featureVector.calculateFeatures(r.readDicts(), articles.get(index).getBody());
        System.out.println(Arrays.toString(featureVector.getNumericFeatures()));
        System.out.println(Arrays.deepToString(featureVector.getLogicFeatures()));
        System.out.println(Arrays.toString(featureVector.getTextFeatures()));
        FeatureVector featureVector2 = new FeatureVector();

        featureVector2.calculateFeatures(r.readDicts(), articles.get(2).getBody());
        System.out.println(Arrays.toString(featureVector2.getNumericFeatures()));
        System.out.println(Arrays.deepToString(featureVector2.getLogicFeatures()));
        System.out.println(Arrays.toString(featureVector2.getTextFeatures()));

// =================================================================

        System.out.println( "Hello End!" );
    }
}
