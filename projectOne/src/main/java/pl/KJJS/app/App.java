package pl.KJJS.app;

import java.io.IOException;
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
        for(Article a : articles){
            System.out.println(a.getPlace());
        }
        System.out.println();
//        System.out.println(Reader.readArticles("input").size());
        Reader r = new Reader();
//        System.out.println(r.readStopList());
    }

}
