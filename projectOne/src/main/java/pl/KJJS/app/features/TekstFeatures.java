package pl.KJJS.app.features;

import pl.KJJS.app.parser.Keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TekstFeatures implements Serializable {
    private static class FeaturesType extends HashMap<ECoreFeature, String> implements Serializable {}
    public final static int eCoreFeaturesBias = 7;
    private static int featuresNumbers;
    private FeaturesType features = new FeaturesType();

    public TekstFeatures() {
        initVariables();
    }

    public static int getFeaturesNumbers() {
        return featuresNumbers;
    }

    public HashMap<ECoreFeature, String> getFeatures() {
        return this.features;
    }

    public String[] getFeaturesAsVector() {
        String[] featuresVector = new String[featuresNumbers];
        for (int i = 0; i < featuresNumbers; i++) {
            featuresVector[i] = features.get(ECoreFeature.values()[i + eCoreFeaturesBias]);
        }
        return featuresVector;
    }

    public String getFeature(ECoreFeature feature) {
        return features.get(feature);
    }

    public void calculateFeatures(HashMap<Keys, HashMap<ECountries, String[][]>> dicts, List<Keys> keys, String[] text) {
        String featureValue;
        List<String> textAsArray= new ArrayList<String>(List.of(text));
        String[] element;
        String[][] dict = new String[ECountries.values().length][];
        for (int i = 0; i < ECountries.values().length; i++) {
            element = dicts.get(keys.get(0)).get(ECountries.values()[i])[0];
            dict[i] = element;
        }

        //TODO delete
        String[][] dict2 = {
                {"west", "germany"}
        };
        String[][] dict3 = {
                {"usa"}
        };
        String[][] dict4 = {
                {"france"}
        };
        String[][] dict5 = {
                {"uk"}
        };
        String[][] dict6 = {
                {"canada"}
        };
        String[][] dict7 = {
                {"japan"}
        };
        HashMap<ECountries, String[][]> newDicts = new HashMap<>();
        newDicts.put(ECountries.west_germany, dict2);
        newDicts.put(ECountries.usa, dict3);
        newDicts.put(ECountries.france, dict4);
        newDicts.put(ECountries.uk, dict5);
        newDicts.put(ECountries.canada, dict6);
        newDicts.put(ECountries.japan, dict7);


        featureValue = calculateFirstCapital(dict, textAsArray);
        features.put(ECoreFeature.tekstFeaturesSto, featureValue);
        featureValue = calculateMostCommonCountry(newDicts, textAsArray);
        features.put(ECoreFeature.tekstFeaturesNajPa, featureValue);
    }

    private String calculateFirstCapital(String[][] dict, List<String> text) {
        String[] featureValue = {""};
        int textLength = text.size();
        int patternLengthCounter;

        for (int i = 0; i < textLength; i++) {
            for (String[] row : dict) {
                patternLengthCounter = 0;
                for (int j = 0; j < row.length; j++) {
                    if (i + patternLengthCounter >= textLength) {
                        break;
                    }
                    if (!row[j].equals(text.get(i + patternLengthCounter))) {
                        break;
                    }
                    patternLengthCounter++;
                }
                if (patternLengthCounter == row.length) {
                    featureValue = row;
                    return String.join(" ", featureValue);
                }
            }
        }
        return String.join(" ", featureValue);
    }

    private String calculateMostCommonCountry(HashMap<ECountries, String[][]> dicts, List<String> text) {
        HashMap<ECountries, Integer> countriesCounter = new HashMap<>();
        for (int i = 0; i < ECountries.values().length; i++) {
            countriesCounter.put(ECountries.values()[i], 0);
        }

        int textLength = text.size();
        int patternLengthCounter;

        for (int i = 0; i < textLength; i++) {
            for (int k = 0; k < dicts.size(); k++) {
                for (String[] row : dicts.get(ECountries.values()[k])) {
                    patternLengthCounter = 0;
                    for (int j = 0; j < row.length; j++) {
                        if (i + patternLengthCounter >= textLength) {
                            break;
                        }
                        if (!row[j].equals(text.get(i + patternLengthCounter))) {
                            break;
                        }
                        patternLengthCounter++;
                    }
                    if (patternLengthCounter == row.length) {
                        Integer tempValue = countriesCounter.get(ECountries.values()[k]);
                        countriesCounter.put(ECountries.values()[k], ++tempValue);
                    }
                }
            }
        }

        Integer max = 0;
        ECountries countryWithMaxValue = null;

        for (int i = 0; i < countriesCounter.size(); i++) {
            Integer value = countriesCounter.get(ECountries.values()[i]);
            if (value > max) {
                max = value;
                countryWithMaxValue = ECountries.values()[i];
            }
        }
        if (max == 0) {
            return "";
        }

        return switch (countryWithMaxValue) {
            case west_germany -> "west germany";
            case usa -> "usa";
            case france -> "france";
            case uk -> "uk";
            case canada -> "canada";
            case japan -> "japan";
        };
    }

    private void initVariables() {
        initFeaturesValues();
        calculateFeaturesNumbers();
    }

    private void initFeaturesValues() {
        features.put(ECoreFeature.tekstFeaturesSto, "");
        features.put(ECoreFeature.tekstFeaturesNajPa, "");
    }

    private void calculateFeaturesNumbers() {
        featuresNumbers = features.size();
    }
}
