package pl.KJJS.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Reader {
    ObjectMapper om;
    Reader(){
        this.om = new ObjectMapper();
    }
    static List<Article> readArticles(String directory) throws IOException {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        List<Article> articles = new LinkedList<Article>();
        for (File file : files){
            System.out.println(file.getPath());
           articles.addAll(SGML.parseArticles(file.getPath()));
        }
        return articles;
    }

    List<String> readStopList() throws IOException {
        return om.readValue(new File("dictionaries/stopList.json"),List.class);
    }
}
