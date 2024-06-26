package pl.KJJS.app.parser;

import pl.KJJS.app.features.ECountries;

import java.io.IOException;
import java.util.Arrays;

public class Article {
    String date;
    String [] topics;
    ECountries place;
    String [] people;
    String [] orgs;
    String [] exchanges;
    String [] companies;
    String title;
    String dateline;
    String[] body;

    public String getDate() {
        return date;
    }

    public String[] getTopics() {
        return topics;
    }

    public ECountries getPlace() {
        return place;
    }

    public String[] getPeople() {
        return people;
    }

    public String[] getOrgs() {
        return orgs;
    }

    public String[] getExchanges() {
        return exchanges;
    }

    public String[] getCompanies() {
        return companies;
    }

    public String getTitle() {
        return title;
    }

    public String getDateline() {
        return dateline;
    }

    public String[] getBody() {
        return body;
    }

    public Article(String date, String[] topics, String place, String[] people, String[] orgs, String[] exchanges, String[] companies, String title, String dateline, String body) throws IOException {
        Tokenizer t = new Tokenizer();
        this.date = date;
        this.topics = topics;
        this.place = switch (place){
            case "usa" -> ECountries.usa;
            case "uk" -> ECountries.uk;
            case "japan" -> ECountries.japan;
            case "west-germany" -> ECountries.west_germany;
            case "canada" -> ECountries.canada;
            case "france" -> ECountries.france;
            default -> ECountries.usa;
        };
        this.people = people;
        this.orgs = orgs;
        this.exchanges = exchanges;
        this.companies = companies;
        this.title = title;
        this.dateline = dateline;
        this.body = t.tokenizeText(body);
    }

    @Override
    public String toString() {
        return "Article{" +
                "date='" + date + '\'' +
                ", topics=" + Arrays.toString(topics) +
                ", places=" + place +
                ", people=" + Arrays.toString(people) +
                ", orgs=" + Arrays.toString(orgs) +
                ", exchanges=" + Arrays.toString(exchanges) +
                ", companies=" + Arrays.toString(companies) +
                ", title='" + title + '\'' +
                ", dateline='" + dateline + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
