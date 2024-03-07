package pl.KJJS.app;

public class Article {
    String date;
    String [] topics;
    String [] places;
    String [] people;
    String [] orgs;
    String [] exchanges;
    String [] companies;
    String title;
    String dateline;
    String body;

    public String getDate() {
        return date;
    }

    public String[] getTopics() {
        return topics;
    }

    public String[] getPlaces() {
        return places;
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

    public String getBody() {
        return body;
    }

    public Article(String date, String[] topics, String[] places, String[] people, String[] orgs, String[] exchanges, String[] companies, String title, String dateline, String body) {
        this.date = date;
        this.topics = topics;
        this.places = places;
        this.people = people;
        this.orgs = orgs;
        this.exchanges = exchanges;
        this.companies = companies;
        this.title = title;
        this.dateline = dateline;
        this.body = body;
    }
}
