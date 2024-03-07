package pl.KJJS.app;

import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        System.out.println( "Hello World!" );
        Reader reader = new Reader();
        reader.readArticles("input/reut2-000.sgm");
    }
}
