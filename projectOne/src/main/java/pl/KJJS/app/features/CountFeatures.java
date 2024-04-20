package pl.KJJS.app.features;

import pl.KJJS.app.parser.Keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountFeatures implements Serializable {
        private static class FeaturesType extends HashMap<ECoreFeature, HashMap<ECountries, Double>> {}
        private static int featuresNumbers;
        private FeaturesType features = new FeaturesType();

    public CountFeatures() {
        initVariables();
    }

    public static int getFeaturesNumbers() {
        return featuresNumbers;
    }

    public HashMap<ECoreFeature, HashMap<ECountries, Double>> getFeatures() {
        return this.features;
    }

    public Double[] getFeaturesAsVector() {
        Double[] featuresVector = new Double[featuresNumbers];
        int coreFeaturesLength;

        for (int i = 0; i < features.size(); i++) {
            coreFeaturesLength = features.get(ECoreFeature.values()[i]).size();
            for (int j = 0; j < coreFeaturesLength; j++) {
                featuresVector[j + i*coreFeaturesLength] = features.get(ECoreFeature.values()[i]).get(ECountries.values()[j]);
            }
        }

        return featuresVector;
    }

    public Double getFeature(ECoreFeature coreFeature, ECountries country) {
        return this.features.get(coreFeature).get(country);
    }

    public void calculateFeatures(HashMap<Keys, HashMap<ECountries,String[][]>> dicts, List<Keys> keys, String[] text) {
        if (keys.size() == features.size()) {
            double featureValue;
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
        features.put(ECoreFeature.countFeaturesGeo, new HashMap<ECountries, Double>());
        features.put(ECoreFeature.countFeaturesObj, new HashMap<ECountries, Double>());
        features.put(ECoreFeature.countFeaturesCit, new HashMap<ECountries, Double>());
        features.put(ECoreFeature.countFeaturesPeop, new HashMap<ECountries, Double>());

        for (int i = 0; i < ECountries.values().length; i++) {
            features.get(ECoreFeature.countFeaturesGeo).put(ECountries.values()[i], 0.0);
            features.get(ECoreFeature.countFeaturesObj).put(ECountries.values()[i], 0.0);
            features.get(ECoreFeature.countFeaturesCit).put(ECountries.values()[i], 0.0);
            features.get(ECoreFeature.countFeaturesPeop).put(ECountries.values()[i], 0.0);
        }
    }

    private void calculateFeaturesNumbers() {
        featuresNumbers = 0;
        for (int i = 0; i < features.size(); i++) {
            for (int j = 0; j < features.get(ECoreFeature.values()[i]).size(); j++) {
                featuresNumbers++;
            }
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
