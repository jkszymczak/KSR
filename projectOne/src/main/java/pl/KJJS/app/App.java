package pl.KJJS.app;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );

//        System.out.println(SGML.parseArticles("input/reut2-017.sgm"));
        System.out.println(Reader.readFiles("input").size());
    }
}
