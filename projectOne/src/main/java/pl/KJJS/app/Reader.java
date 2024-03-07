package pl.KJJS.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {


    private static Article readArticle(String s){
        System.out.println(new Scanner(s).useDelimiter("</?DATE>").tokens().findFirst().get());
        System.out.println("end");
        String [] lol = {""};
        return new Article("",lol,lol,lol,lol,lol,lol,"","","");
    }
    static Article[] readArticles(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner scan = new Scanner(file).useDelimiter("</?REUTERS.*>");
        scan.nextLine();
        System.out.println(scan.tokens().map(String::trim).filter((s)->{
            return !s.isEmpty();
        }).map(Reader::readArticle).toList());


        return new Article[0];
    }
}
