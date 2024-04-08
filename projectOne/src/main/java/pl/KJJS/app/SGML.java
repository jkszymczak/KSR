package pl.KJJS.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class SGML {
    /**
     * Parse one occurence of given token, if not found token is null, everything before token is ignored
     * @param text input text to parse
     * @param token format is < 'token' >, token limiting value looked for
     * @return Result token is found value
     * */
    public static Result<String> parseToken(String text,String token){
        String [] buf = text.split("<"+token+">",2);
        if(buf.length==1){
            return new Result<String>(null,text);
        }
        buf = buf[1].split("</"+token+">",2);
        if(buf.length==1){
            return new Result<String>(null,text);
        }
        return new Result<String>(buf[0],buf[1]);
    }


    /**
     * Parse many occurences of given token, never fails
     * */
    public static Result<String[]> many(String text,String token){
        List<String> result = new LinkedList<String>();
        String txt = text;

        while (true){
            Result<String> run = parseToken(txt,token);
            txt = run.rest;
            if(!run.isOk()) {
                break;
            }
            result.add(run.token);
        }
        return new Result<String[]>(result.toArray(new String[0]), txt);
    }

    /**
     * sequence parsing given tokens one by one, returns null as a token when fails
     * */
    public static Result<String[]> sequence(String text, String[] tokens){
        List<String> result = new LinkedList<String>();
        String txt = text;

        for (String token :tokens){
            Result<String> run = parseToken(txt,token);
            txt = run.rest;
            if(!run.isOk()) {
                return new Result<String[]>(null,text);
            }
            result.add(run.token);
        }
        if(result.isEmpty()) return new Result<String[]>(null,text);
        return new Result<String[]>(result.toArray(new String[0]), txt);
    }

    /**
     * consume input with given token, return only not consumed part of input string
     * */
    public static String consumeToken(String text, String token){
        return parseToken(text,token).rest;
    }

    public static String[] parseText(String text){
        List<String> result = new LinkedList<String>();
        Result<String> title = parseToken(text,"TITLE");
        Boolean b = (title.token != null) ? result.add(title.token) : result.add("");
        String txt = consumeToken(title.rest, "AUTHOR");
        Result<String> dateline = parseToken(txt,"DATELINE");
        b = (dateline.token != null) ? result.add(dateline.token) : result.add("");
        Result<String> body = parseToken(dateline.rest, "BODY");
        b = (body.token != null) ? result.add(body.token) : result.add("");

        return result.toArray(new String[0]);

    }

    public static Article parseArticle(String s) throws IOException {

        String[] baseTokens = {"DATE","TOPICS","PLACES","PEOPLE","ORGS","EXCHANGES","COMPANIES"};
        String[] tmpCountries = {"uk","japan","canada","west-germany","usa"};
        List<String > ts = Stream.of(tmpCountries).toList();
        Result<String[]> base = sequence(s,baseTokens);

        String date = base.token[0];
        String[] topics = many(base.token[1],"D").token;
        String[] places = many(base.token[2],"D").token;
        if(places.length != 1) return null;
        if(!ts.contains(places[0])) return null;
        String[] people = many(base.token[3],"D").token;
        String[] orgs = many(base.token[4],"D").token;
        String[] exchanges = many(base.token[5],"D").token;
        String[] companies = many(base.token[6],"D").token;
        String txt = consumeToken(base.rest,"UNKNOWN");

        String[] text = parseText(txt);

        return new Article(date,topics,places[0],people,orgs,exchanges,companies,text[0],text[1],text[2]);
    }
    public static List<Article> parseArticles(String path) throws IOException {

        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);

        Path p = Paths.get(path);
        BufferedReader br = new BufferedReader(isr, (int) Files.size(p));

        Scanner scan = new Scanner(br).useDelimiter("</?REUTERS.*>");

        //Skip DOCTYPE
        scan.nextLine();

        List<Article> articles = scan.tokens().map(String::trim).filter((s)->{
            return !s.isEmpty();
        }).map(s1 ->{
            try{return parseArticle(s1);}
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        } ).filter(a -> a!=null).toList();
        scan.close();
        br.close();
        isr.close();
        fis.close();
        return articles;
    }
}
