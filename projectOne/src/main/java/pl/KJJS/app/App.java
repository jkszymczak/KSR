package pl.KJJS.app;

import pl.KJJS.app.parser.Article;
import pl.KJJS.app.parser.Reader;
import pl.KJJS.app.parser.SGML;

import java.io.IOException;
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
        List<Article> articles = SGML.parseArticles("input/reut2-017.sgm");

        System.out.println(articles.get(0).getPlace());
        System.out.println(articles.get(0).getTitle());
        System.out.println(Arrays.toString(Stream.of(articles.get(0).getBody()).toArray()));

//        for(Article a : articles){
//            System.out.println(a.getPlace());
//        }
//        System.out.println();
//        System.out.println(Reader.readArticles("input").size());
        Reader r = new Reader();
        r.readDict("dictionaries/cities.json");
//        System.out.println(r.readStopList());
    }

}
