package pl.KJJS.app;

import org.apache.commons.cli.*;
import pl.KJJS.app.features.*;
import pl.KJJS.app.knn.KNN;
import pl.KJJS.app.metrics.ChebyshevMetric;
import pl.KJJS.app.metrics.EuclideanMetric;
import pl.KJJS.app.metrics.ManhattanMetric;
import pl.KJJS.app.metrics.Metric;
import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Keys;
import pl.KJJS.app.parser.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Options options = new Options();
//        Option test = new Option("t", "test", true, "test");
//        test.setRequired(true);
//        options.addOption(test);

        Option dict = new Option("d", "dictionaries", true, "directory from where read all dictionaries");
//        dict.setRequired(true);
        options.addOption(dict);

        Option inputs = new Option("i", "inputs", true, "directory from which to read all input files");
        inputs.setRequired(true);
        options.addOption(inputs);

        Option kValue = Option.builder("k").hasArgs().valueSeparator(',').argName("k").desc( "n values of k of k-NN alghoritm").build();
                // new Option("k", "k", true, "n values of k of k-NN alghoritm");
        kValue.setRequired(true);
        options.addOption(kValue);

        Option metric = new Option("m", "metric", true, "One of metrics to use to calculate distances between vectors");
        options.addOption(metric);

        Option file = new Option("o", "output", true, "output file");
        file.setRequired(true);
        options.addOption(file);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;//not a good practice, it serves it purpose

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        System.out.println(cmd.getOptionValue("o"));

        System.out.println( "Hello World!" );
        String metricName = cmd.getOptionValue("m");

        Metric m = switch (metricName){
            case "Euclidean" -> new EuclideanMetric();
            case "Manhattan" -> new ManhattanMetric();
            case "Chebyshev" -> new ChebyshevMetric();
            default -> new EuclideanMetric();
        };

        String input = cmd.getOptionValue("i");
        String output = cmd.getOptionValue("o");
        String dir = (cmd.getOptionValue("d")==null) ? "dictionaries" : cmd.getOptionValue("d");
        String[] kValues = cmd.getOptionValues("k");
        int[] k = new int[kValues.length];
        for (int i = 0; i < kValues.length; i++) {
            k[i] = Integer.parseInt(kValues[i]);
        }

        Reader r = new Reader();
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts(dir);
        List<Article> articles =Reader.readArticles(input).stream().toList();
        List<ArticleFeature> vectors = new ArrayList<>();

        for (Article article : articles) {
            vectors.add(new ArticleFeature(article,dicts));
        }
        List<ArticleFeature> learnSet = vectors.subList(0,200);
        System.out.println(learnSet.size());
        List<ArticleFeature> testSet = vectors.subList(200,1000);
        System.out.println(testSet.size());
        KNN kNN = new KNN(learnSet);
        kNN.rateToFile(testSet,m,k,output);
//        System.out.println(Reader.readArticles("input").size());
//        Reader r = new Reader();
//        r.readDicts();
//        System.out.println(r.readStopList());

//        System.out.println("Number of articles: " + articles.size());
//        System.out.println("Read done!");
//        int index = 0;
//
//        System.out.println(Arrays.toString(Stream.of(articles.get(index).getBody()).toArray()));

// =================================================================
//        LiczFeatures liczFeatures = new LiczFeatures();
        // !!! Keys must be the same order as the ECoreFeature enum !!!
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

//        FeatureVector featureVector = new FeatureVector();
//        featureVector.calculateFeatures(r.readDicts(), articles.get(index).getBody());
//        System.out.println(Arrays.toString(featureVector.getNumericFeatures()));
//        System.out.println(Arrays.deepToString(featureVector.getLogicFeatures()));
//        System.out.println(Arrays.toString(featureVector.getTextFeatures()));
//        FeatureVector featureVector2 = new FeatureVector();
//
//        featureVector2.calculateFeatures(r.readDicts(), articles.get(2).getBody());
//        System.out.println(Arrays.toString(featureVector2.getNumericFeatures()));
//        System.out.println(Arrays.deepToString(featureVector2.getLogicFeatures()));
//        System.out.println(Arrays.toString(featureVector2.getTextFeatures()));

// =================================================================

        System.out.println( "Hello End!" );
    }
}
