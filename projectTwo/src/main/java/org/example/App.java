package org.example;

import Database.CSV;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String path = "dataBasePrep/prepared.csv";

        System.out.println( "Hello World!" );
        CSV.readCSV(path);
    }
}
