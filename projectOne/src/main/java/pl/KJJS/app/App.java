package pl.KJJS.app;

import pl.KJJS.app.features.LiczFeatures;
import pl.KJJS.app.parser.Keys;
import pl.KJJS.app.parser.Reader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );

        Reader r = new Reader();


        LiczFeatures liczFeatures = new LiczFeatures();
        liczFeatures.calculateFeatures(r.readDicts(), new ArrayList<Keys>(){{
            add(Keys.geographic_locations);
            add(Keys.architectural_objects);
            add(Keys.cities);
            add(Keys.fameous_people);
        }});
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
