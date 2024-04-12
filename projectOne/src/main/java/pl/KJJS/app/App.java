package pl.KJJS.app;

import pl.KJJS.app.features.LiczFeatures;
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

        System.out.println("Number of articles: " + articles.size());
        System.out.println("Read done!");

        int index = 1;

        System.out.println(Arrays.toString(Stream.of(articles.get(index).getBody()).toArray()));



        LiczFeatures liczFeatures = new LiczFeatures();
        liczFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>(){{
            add(Keys.geographic_locations);
            add(Keys.architectural_objects);
            add(Keys.cities);
            add(Keys.fameous_people);
        }}, articles.get(index).getBody());

//        String[][] dicts = {{"statue", "of", "liberty"},
//                {"mountains", "of", "liberty"},
//                {"new", "york"}};
//        List<String> text = new ArrayList<>();
//        text.add("example");
//        text.add("statue");
//        text.add("of");
//        text.add("liberty");
//        text.add("example");
//        text.add("mountains");
//        text.add("of");
//        text.add("liberty");

//        System.out.println(liczFeatures.calculateSingleFeature(dicts, text));
//        double x = liczFeatures.getFeature(ECoreFeature.liczFeaturesGeo, ECountry.canada);

//        List<Article> articles = SGML.parseArticles("input/reut2-017.sgm");
//
//        System.out.println(articles.get(0).getPlace());
//        System.out.println(articles.get(0).getTitle());
//        System.out.println(Arrays.toString(Stream.of(articles.get(0).getBody()).toArray()));
//
////        for(Article a : articles){
////            System.out.println(a.getPlace());
////        }
////        System.out.println();
////        System.out.println(Reader.readArticles("input").size());
//        Reader r = new Reader();
//        r.readDicts();
//        System.out.println(r.readStopList());
        System.out.println( "Hello End!" );

    }
}
