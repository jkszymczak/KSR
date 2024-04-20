package pl.KJJS.app.features;

import pl.KJJS.app.parser.Keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FeatureVector implements MultiFeature, Serializable {
    private CountFeatures countFeatures = new CountFeatures();
    private HowManyFeatures howManyFeatures = new HowManyFeatures();
    private AreFeatures areFeatures = new AreFeatures();
    private TextFeatures textFeatures = new TextFeatures();

    @Override
    public Boolean[][] getLogicFeatures() {
        return areFeatures.getFeaturesAsVector();
    }

    @Override
    public Double[] getNumericFeatures() {
        Double[] numericFeatures_1 = countFeatures.getFeaturesAsVector();
        Double[] numericFeatures_2 = howManyFeatures.getFeaturesAsVector();
        Double[] combinedFeatures = new Double[numericFeatures_1.length + numericFeatures_2.length];

        System.arraycopy(numericFeatures_1, 0, combinedFeatures, 0, numericFeatures_1.length);
        System.arraycopy(numericFeatures_2, 0, combinedFeatures, numericFeatures_1.length, numericFeatures_2.length);

        return combinedFeatures;
    }

    @Override
    public String[] getTextFeatures() {
        return textFeatures.getFeaturesAsVector();
    }

    public void calculateFeatures(HashMap<Keys, HashMap<ECountries, String[][]>> dicts, String[] text) {
        countFeatures.calculateFeatures(dicts, new ArrayList<Keys>() {{
            add(Keys.geographic_locations);
            add(Keys.architectural_objects);
            add(Keys.cities);
            add(Keys.fameous_people);
        }}, text);
        howManyFeatures.calculateFeatures(dicts, new ArrayList<Keys>() {{
            add(Keys.characteristic_words);
            // add dict to continents
        }}, text);
        areFeatures.calculateFeatures(dicts, new ArrayList<Keys>() {{
            add(Keys.institutions);
            add(Keys.cities);
            add(Keys.dates);
        }}, text);
        textFeatures.calculateFeatures(dicts, new ArrayList<Keys>() {{
            add(Keys.cities);
            // add dict to countries
        }}, text);
    }

    /*
    List of the number of features in the feature vector (with numbering from the report):

    *6 - means that the feature is calculated separately for each of the 6 countries

    1 - licz  *6 = 6
    2 - licz  *6 = 6
    3 - licz  *6 = 6
    4 - licz  *6 = 6

    5 - ile *6 = 6
    6 - ile = 1
    7 - ile = 1

    8  - czy = 1
    9  - czy = 1
    10 - czy = 1

    11 - tekst = 1
    12 - tekst = 1

    Sum = 37
     */
}
