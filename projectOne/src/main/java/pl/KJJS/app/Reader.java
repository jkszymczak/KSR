package pl.KJJS.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Reader {
    static List<Article> readFiles(String directory) throws IOException {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        List<Article> articles = new LinkedList<Article>();
        for (File file : files){
            System.out.println(file.getPath());
           articles.addAll(SGML.parseArticles(file.getPath()));
        }
        return articles;
    }
}
