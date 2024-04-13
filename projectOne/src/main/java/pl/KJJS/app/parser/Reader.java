package pl.KJJS.app.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.KJJS.app.features.ECountries;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Reader {
    ObjectMapper om;
//    private HashMap<String, String[]> dict;

    public Reader(){
        this.om = new ObjectMapper();
    }
    public static List<Article> readArticles(String directory) throws IOException {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        List<Article> articles = new LinkedList<Article>();
        for (File file : files){
//            System.out.println(file.getPath());
           articles.addAll(SGML.parseArticles(file.getPath()));
        }
        return articles;
    }

    List<String> readStopList() throws IOException {
        return om.readValue(new File("dictionaries/stopList.json"),List.class);
    }

    private String[][] splitToArray(List<String> arr){
        String[][] result = new String[arr.size()][];
        for(int i=0;i<arr.size();i++){
            String[] split= arr.get(i).split("\\W+");

            result[i] = Stream.of(split).map(String::toLowerCase).filter(s -> !s.isEmpty()).toList().toArray(new String[0]);
        }
        return result;
    }

//    public Dictionary readDict(String path) throws IOException {
//        HashMap<String,List<String>> dict = this.om.readValue(new File(path), HashMap.class);
//        HashMap<String ,String[][]> result = new HashMap<>();
//        dict.forEach((key,value) -> result.put(key,splitToArray(value)));
//
//        return new Dictionary(result);
//    }
//    public HashMap<Keys,Dictionary> readDicts() throws IOException {
//        String dir = "dictionaries/";
//        HashMap<Keys, Dictionary> result = new HashMap<>();
////        String[] keys = {"architectural-objects","characteristic-words","cities","dates","fameous-people","geographic-locations","institutions","stopList"};
//        for(Keys key:Keys.values()){
//            result.put(key,readDict(dir+key+".json"));
//        }
//
//        return result;
//    }
    public HashMap<ECountries,String[][]> readDict(String path) throws IOException {
        HashMap<String,List<String>> dict = this.om.readValue(new File(path), HashMap.class);
    
        HashMap<ECountries,String[][]> result = new HashMap<>();
        dict.forEach((key,value) -> {
            ECountries c = switch (key) {
                case "West Germany" -> ECountries.west_germany;
                case "UK" -> ECountries.uk;
                case "USA" -> ECountries.usa;
                case "Japan" -> ECountries.japan;
                case "Canada" -> ECountries.canada;
                case "France" -> ECountries.france;
                default -> null;
            };
            result.put(c,splitToArray(value));
        });
    
        return result;
    }
        public HashMap<Keys,HashMap<ECountries,String[][]>> readDicts() throws IOException {
            String dir = "dictionaries/";
            HashMap<Keys, HashMap<ECountries,String[][]>> result = new HashMap<>();
    //        String[] keys = {"architectural-objects","characteristic-words","cities","dates","fameous-people","geographic-locations","institutions","stopList"};
            for(Keys key:Keys.values()){
                result.put(key,readDict(dir+key+".json"));
            }
    
            return result;
        }
}
