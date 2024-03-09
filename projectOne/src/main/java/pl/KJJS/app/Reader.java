package pl.KJJS.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Reader {


    private static String preprocess(String s, String pattern){
        String tryout = s.replaceAll(pattern,"");
        if(tryout.isBlank()) {
//            System.out.println("Only pattern found");
            return "";
        }
        String [] wut = s.split(pattern);
        String res = wut[0];
//        System.out.println(res);

        return res;
    }
    private static String [] splitToArr (String s){
        String [] splited = s.split("<D>");
        for (int i = 0;i< splited.length;i++){
            splited[i] = preprocess(splited[i],"</D>");
        }
        List<String> cleaned = new LinkedList<String>();
        for(String st:splited){
            if(!st.isBlank()) cleaned.add(st);
        }
//        System.out.println(splited[0]);
        return cleaned.toArray(new String[0]);
    }
    public static String [] parseText(String s){
        String rest = s.split("<TITLE>")[1];
        String [] buf = new String[2];
        List<String> result = new LinkedList<String>();
        buf = rest.split("</TITLE>\n?<AUTHOR>");
        if(buf.length==2){
            String tmp = buf[0];
            buf = buf[1].split("</AUTHOR>\n?<DATELINE>");
            buf[0]=tmp;
        } else {
            buf = buf[0].split("</TITLE>\n?<DATELINE>");
        }

        result.add(buf[0]);
        buf = buf[1].split("</DATELINE>\n?<BODY>");
        result.add(buf[0]);
        buf[1] = preprocess(buf[1],"</BODY>");
        result.add(buf[1]);

        return result.toArray(new String[0]);
    }
    private static Article readArticle(String s){
        List<String> scanner =  new Scanner(s).useDelimiter("<DATE>|<TOPICS>|<PLACES>|<PEOPLE>|" +
                "<ORGS>|<EXCHANGES>|<COMPANIES>|<TEXT.*>|<UNKNOWN>").tokens().toList();
        String [] patterns = {"</DATE>",
                "</TOPICS>","</PLACES>","</PEOPLE>",
                "</ORGS>","</EXCHANGES>",
                "</COMPANIES>","</UNKNOWN>","</TEXT>"};
        String [] tokens = new String[9];
        for(int i = 0;i< scanner.size();i++){
            String token = scanner.get(i);
            tokens[i] = preprocess(token,patterns[i]);
        }
//        System.out.println(tokens[1].getClass());
        String date = tokens[0];
        String [] topics    = splitToArr(tokens[1]);
        String [] places    = splitToArr(tokens[2]);
        String [] people    = splitToArr(tokens[3]);
        String [] orgs      = splitToArr(tokens[4]);
        String [] exchanges = splitToArr(tokens[5]);
        String [] companies = splitToArr(tokens[6]);
//        String [] text = parseText(tokens[8]);
//        System.out.println(tokens);
        String [] lol = {""};


//        return new Article(date,topics,places,people,orgs,exchanges,companies,text[0],text[1],text[2]);
        return new Article(date,topics,places,people,orgs,exchanges,companies,"","","");
    }
    public static Article[] readArticles(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner scan = new Scanner(file).useDelimiter("</?REUTERS.*>");
        scan.nextLine();
        System.out.println(scan.tokens().map(String::trim).filter((s)->{
            return !s.isEmpty();
        }).map(Reader::readArticle).toList().get(1));


        return new Article[0];
    }
}
