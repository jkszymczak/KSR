package LinguisticSummarization;

public enum Subject {
    SUB_HOUR_OCEAN("<1H OCEAN"),
    INLAND("INLAND"),
    NEAR_OCEAN("NEAR OCEAN"),
    NEAR_BAY("NEAR BAY"),
    ISLAND("ISLAND");
    public final String label;
    Subject(String s) {
        this.label = s;
    }
}
