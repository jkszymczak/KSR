package pl.KJJS.app.features;

import pl.KJJS.app.parser.Keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class LiczFeatures {
    public static class FeaturesType extends HashMap<ECoreFeature, HashMap<ECountries, Double>> {}
    private static int featuresNumbers;
    private FeaturesType features = new FeaturesType();

    public LiczFeatures() {
        initVariables();
    }

    public static int getFeaturesNumbers() {
        return featuresNumbers;
    }

    public void calculateFeaturesNumbers() {
        featuresNumbers = 0;
        for (int i = 0; i < features.size(); i++) {
            for (int j = 0; j < features.get(ECoreFeature.values()[i]).size(); j++) {
                featuresNumbers++;
            }
        }
    }

    public FeaturesType getFeatures() {
        return this.features;
    }

//    public Double[] getFeaturesAsVector() {
//
//
//
//    }

    public Double getFeature(ECoreFeature coreFeature, ECountries country) {
        return this.features.get(coreFeature).get(country);
    }

    public void calculateFeatures(HashMap<Keys, HashMap<ECountries,String[][]>> dicts, List<Keys> keys, String[] text) {
        if (keys.size() == features.size()) {
            double featureValue = 0.;
            List<String> textAsArray= new ArrayList<String>(List.of(text));
            for (int i = 0; i < features.size(); i++) {
                for (int j = 0; j < features.get(ECoreFeature.values()[i]).size(); j++) {
                    featureValue = calculateSingleFeature(dicts.get(keys.get(i)).get(ECountries.values()[j]), textAsArray);
                    features.get(ECoreFeature.values()[i]).put(ECountries.values()[j], featureValue);
                }
            }
        } else {
            throw new IllegalArgumentException("The number of keys and the number of features are not equal!");
        }
        System.out.println("To delete"); // create for breakpoint
    }

    private Double calculateSingleFeature(String[][] dict, List<String> text) {
        int textLength = text.size();
        double featureValue = 0.;
        int patternLengthCounter;

        for (int i = 0; i < textLength; i++) {
            for (String[] row : dict) {
                patternLengthCounter = 0;
                for (int j = 0; j < row.length; j++) {
                    if (i+patternLengthCounter >= textLength) {
                        break;
                    }
                    if (!row[j].equals(text.get(i+patternLengthCounter))) {
                        break;
                    }
                    patternLengthCounter++;
                }
                if (patternLengthCounter == row.length) {
                    featureValue += row.length;
                }
            }
        }

        return featureValue / textLength;
    }

    private void initVariables() {
        initFeaturesValues();
        calculateFeaturesNumbers();
    }

    private void initFeaturesValues() {
        features.put(ECoreFeature.liczFeaturesGeo, new HashMap<ECountries, Double>());
        features.put(ECoreFeature.liczFeaturesObi, new HashMap<ECountries, Double>());
        features.put(ECoreFeature.liczFeaturesMiast, new HashMap<ECountries, Double>());
        features.put(ECoreFeature.liczFeaturesOsob, new HashMap<ECountries, Double>());

        for (int i = 0; i < ECountries.values().length; i++) {
            features.get(ECoreFeature.liczFeaturesGeo).put(ECountries.values()[i], 0.0);
            features.get(ECoreFeature.liczFeaturesObi).put(ECountries.values()[i], 0.0);
            features.get(ECoreFeature.liczFeaturesMiast).put(ECountries.values()[i], 0.0);
            features.get(ECoreFeature.liczFeaturesOsob).put(ECountries.values()[i], 0.0);
        }
    }

    /*
    Nr.     - Feature name
    0-5     - liczFeaturesGeo
    6-11    - liczFeaturesObi
    12-17   - liczFeaturesMiast
    18-23   - liczFeaturesOsob
     */
}