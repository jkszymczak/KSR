package pl.KJJS.app;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

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
//        inputs.setRequired(true);
        options.addOption(inputs);

        Option kValue = Option.builder("k").hasArgs().valueSeparator(',').argName("k").desc( "n values of k of k-NN alghoritm, separated by ,").build();
        kValue.setRequired(true);
        options.addOption(kValue);

        Option metric = new Option("m", "metric", true, "One of metrics to use to calculate distances between vectors");
        options.addOption(metric);

        Option file = new Option("o", "output", true, "output file");
//        file.setRequired(true);
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
            throw new IllegalArgumentException("Value of k is not a number");
        }

        return k;
    }

    static int[] countProportions(String[] input,int size){
        if(input.length != 2){
            throw new IllegalArgumentException("Proportions must have exactly two integers");
        }
        int[] k = new int[input.length];
        try{
            for (int i=0; i<input.length; i++){
                k[i] = Integer.parseInt(input[i]);
            }
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Value of proportion is not a number");
        }

        int whole = Arrays.stream(k).sum();
        int part = Math.floorDiv(size,whole);

        k[0] *= part;
        k[1] = size-k[0];
        return k;
    }

    public static void main( String[] args ) throws IOException {
        AnsiFormat done = new AnsiFormat(Attribute.GREEN_TEXT(),Attribute.BOLD());
        AnsiFormat info = new AnsiFormat(Attribute.ITALIC());
        CommandLine cmd = readArguments(declareOptions(),args);
        String input = (cmd.getOptionValue("i")==null) ? "input" : cmd.getOptionValue("i");
        String output = (cmd.getOptionValue("o")==null) ? "output.csv" : cmd.getOptionValue("o");
        String dir = (cmd.getOptionValue("d")==null) ? "dictionaries" : cmd.getOptionValue("d");
        String[] prop = (cmd.getOptionValues("p")==null)? new String[]{"30", "70"} : cmd.getOptionValues("p");
        String lim = cmd.getOptionValue("l");
        Metric m = matchMetric(cmd.getOptionValue("m"));
        int[] k = readK(cmd.getOptionValues("k"));

        System.out.println(colorize("Parameters: ",Attribute.BOLD()));
        System.out.print("\t "+colorize("k",info)+" = [ ");
        for (int v:k){
            System.out.print(colorize(String.valueOf(v),Attribute.BOLD())+" ");
        }
        System.out.print("] \n");
        System.out.println("\t "+colorize("Metric",info)+": "+ colorize(m.getClass().getSimpleName(), Attribute.BOLD()));
        System.out.println("\t "+colorize("Proportions",info)+": "+colorize(prop[0]+":"+prop[1], Attribute.BOLD()));
        if(lim != null) System.out.println("\t "+colorize("Limited to",info)+": "+colorize(lim,Attribute.BOLD()));
        System.out.println("\t "+colorize("Input directory",info)+": "+colorize(input,Attribute.BOLD()));
        System.out.println("\t "+colorize("Output file",info)+": "+colorize(output,Attribute.BOLD()));


        Reader r = new Reader();

        System.out.print(colorize("Loading dictionaries...",info));
        HashMap<Keys, HashMap<ECountries, String[][]>> dicts = r.readDicts(dir);
        System.out.print(colorize("\t DONE \n",done));

        System.out.print(colorize("Loading input files...",info));
        List<Article> articles = (lim == null) ? Reader.readArticles(input).stream().toList()
                : Reader.readArticles(input).stream().limit(Integer.parseInt(lim)).toList();
        System.out.print(colorize("\t DONE \n",done));

        List<ArticleFeature> vectors = new ArrayList<>();

        System.out.print(colorize("Calculating feature vectors...",info));
        for (Article article : articles) {
            vectors.add(new ArticleFeature(article,dicts));
        }
        System.out.print(colorize("\t DONE \n", done));

        int[] p = countProportions(prop,vectors.size());

        List<ArticleFeature> learnSet = vectors.subList(0,p[0]);
        System.out.println(colorize("Learning set size",info)+": "+colorize(String.valueOf(learnSet.size()),Attribute.BOLD()));

        List<ArticleFeature> testSet = vectors.subList(p[0], vectors.size());
        System.out.println(colorize("Testing set size",info)+": "+colorize(String.valueOf(testSet.size()),Attribute.BOLD()));

        KNN kNN = new KNN(learnSet);
        System.out.println(colorize("Starting kNN...",Attribute.GREEN_BACK(),Attribute.ITALIC()));
        kNN.rateToFile(testSet,m,k,output);
        System.out.println(colorize("kNN is DONE \n",Attribute.GREEN_BACK(),Attribute.ITALIC()));


    }
}
