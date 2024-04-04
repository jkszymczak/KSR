package pl.KJJS.app;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );

        System.out.println(SGML.parseArticles("input/reut2-017.sgm"));
//        System.out.println(Reader.readArticles("input").size());
        Reader r = new Reader();
        System.out.println(r.readStopList());
    }

}
