package pl.KJJS.app;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {
    List<String> stopList;
    Tokenizer() throws IOException {
        Reader r = new Reader();
        this.stopList = r.readStopList();
    }

    /**
     * Function to tokenize text, it splits entered string to words, applies to lower, and filters every occurence of words from stop list
     * @param text - text to tokenize
     * @return - tokenized text
     */
    String[] tokenizeText(String text){
        String[] words = text.split("\\W+");
        return Stream.of(words).map(String::toLowerCase).
                filter(s -> !stopList.contains(s)).collect(Collectors.toList()).
                toArray(new String[0]);
    }
}
