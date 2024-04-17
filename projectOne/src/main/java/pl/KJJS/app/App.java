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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    static Options declareOptions(){
        Options options = new Options();

        Option dict = new Option("d", "dictionaries", true, "directory from where read all dictionaries");
        options.addOption(dict);

        Option inputs = new Option("i", "inputs", true, "directory from which to read all input files");
        inputs.setRequired(true);
        options.addOption(inputs);

        Option kValue = Option.builder("k").hasArgs().valueSeparator(',').argName("k").desc( "n values of k of k-NN alghoritm").build();
        kValue.setRequired(true);
        options.addOption(kValue);

        Option metric = new Option("m", "metric", true, "One of metrics to use to calculate distances between vectors");
        options.addOption(metric);

        Option file = new Option("o", "output", true, "output file");
        file.setRequired(true);
        options.addOption(file);

        Option proportions = Option.builder("p").hasArgs()
                .valueSeparator(':').argName("proportions")
                .desc("Proportions between learning and testing set. written as two integers separated by :, default value 30:70").build();
        options.addOption(proportions);

        Option limit = new Option("l", "limit", true, "limiting number of articles");
        options.addOption(limit);
        return options;
    }

    static CommandLine readArguments(Options op,String[] args){
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;//not a good practice, it serves it purpose

        try {
            cmd = parser.parse(op, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Reuters article classifier", op);

            System.exit(1);
        }
        return cmd;
    }

    static Metric matchMetric(String input){
        List<String> metrics = new ArrayList<>();
        metrics.add("Euclidean");
        metrics.add("Manhattan");
        metrics.add("Chebyshev");
        if(!metrics.contains(input)){
            throw new IllegalArgumentException("Input is not a metric");
        }

        Metric m = switch (input){
            case "Euclidean" -> new EuclideanMetric();
            case "Manhattan" -> new ManhattanMetric();
            case "Chebyshev" -> new ChebyshevMetric();
            default -> new EuclideanMetric();
        };
        return m;
    }

    static int[] readK(String[] input){
        int[] k = new int[input.length];
        try{
            for (int i = 0; i < input.length; i++) {
                k[i] = Integer.parseInt(input[i]);
            }
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Input is not a number");
        }

        return k;
    }

    public static void main( String[] args ) throws IOException {
        CommandLine cmd = readArguments(declareOptions(),args);


        String input = cmd.getOptionValue("i");
        String output = cmd.getOptionValue("o");
        String dir = (cmd.getOptionValue("d")==null) ? "dictionaries" : cmd.getOptionValue("d");
        String[] prop = cmd.getOptionValues("p");
        String lim = cmd.getOptionValue("l");
        Metric m = matchMetric(cmd.getOptionValue("m"));

        int[] k = readK(cmd.getOptionValues("k"));

        Reader r = new Reader();
        System.out.print("Loading dictionaries...");
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts(dir);
        System.out.print("\t DONE \n");
        System.out.print("Loading input files...");
        List<Article> articles = (lim == null) ? Reader.readArticles(input).stream().toList()
                : Reader.readArticles(input).stream().limit(Integer.parseInt(lim)).toList();
        System.out.print("\t DONE \n");
        List<ArticleFeature> vectors = new ArrayList<>();
        System.out.print("Calculating feature vectors...");
        for (Article article : articles) {
            vectors.add(new ArticleFeature(article,dicts));
        }
        System.out.print("\t DONE \n");

        List<ArticleFeature> learnSet = vectors.subList(0,200);
        System.out.println(learnSet.size());
        List<ArticleFeature> testSet = vectors.subList(200, vectors.size());
        System.out.println(testSet.size());
        KNN kNN = new KNN(learnSet);
        kNN.rateToFile(testSet,m,k,output);

        System.out.println( "Hello End!" );
    }
}
