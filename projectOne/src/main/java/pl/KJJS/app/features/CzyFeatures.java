package pl.KJJS.app.features;

import pl.KJJS.app.parser.Keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CzyFeatures implements Serializable {
    private static class FeaturesType extends HashMap<ECoreFeature, HashMap<ECountries, Boolean>> {}
    public final static int eCoreFeaturesBias = 5;
    private static int featuresNumbers;
    private FeaturesType features = new FeaturesType();

    public CzyFeatures() {
        initVariables();
    }

    public static int getFeaturesNumbers() {
        return featuresNumbers;
    }

    public HashMap<ECoreFeature, HashMap<ECountries, Boolean>> getFeatures() {
        return this.features;
    }

    public Boolean[][] getFeaturesAsVector() {
        Boolean[][] featuresVector = new Boolean[featuresNumbers][ECountries.values().length];
        int coreFeaturesLength;
        for (int i = 0; i < features.size(); i++) {
            coreFeaturesLength = features.get(ECoreFeature.values()[i + eCoreFeaturesBias]).size();
            for (int j = 0; j < coreFeaturesLength; j++) {
                featuresVector[i][j] = features.get(ECoreFeature.values()[i + eCoreFeaturesBias]).get(ECountries.values()[j]);
            }
        }
        return featuresVector;
    }

    public Boolean getFeature(ECoreFeature coreFeature, ECountries country) {
        return this.features.get(coreFeature).get(country);
    }

    public void calculateFeatures(HashMap<Keys, HashMap<ECountries, String[][]>> dicts, List<Keys> keys, String[] text) {
        boolean featureValue;
        List<String> textAsArray= new ArrayList<String>(List.of(text));
        String[][] dict = {{""}};
        for (int i = 0; i < features.size(); i++) {
            if (ECoreFeature.values()[i + eCoreFeaturesBias] == ECoreFeature.czyFeaturesSto) {
                for (int j = 0; j < features.get(ECoreFeature.values()[i + eCoreFeaturesBias]).size(); j++) {
                    dict[0] = dicts.get(keys.get(i)).get(ECountries.values()[j])[0];
                    featureValue = calculateSingleFeature(dict, textAsArray);
                    features.get(ECoreFeature.values()[i + eCoreFeaturesBias]).put(ECountries.values()[j], featureValue);
                }
            } else {
                for (int j = 0; j < features.get(ECoreFeature.values()[i + eCoreFeaturesBias]).size(); j++) {
                    featureValue = calculateSingleFeature(dicts.get(keys.get(i)).get(ECountries.values()[j]), textAsArray);
                    features.get(ECoreFeature.values()[i + eCoreFeaturesBias]).put(ECountries.values()[j], featureValue);
                }
            }
        }
    }

    private Boolean calculateSingleFeature(String[][] dict, List<String> text) {
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
                    return true;
                }
            }
        }
        return false;
    }


    private void initVariables() {
        initFeaturesValues();
        calculateFeaturesNumbers();
    }

    private void initFeaturesValues() {
        features.put(ECoreFeature.czyFeaturesInst, new HashMap<ECountries, Boolean>());
        features.put(ECoreFeature.czyFeaturesSto, new HashMap<ECountries, Boolean>());
//        features.put(ECoreFeature.czyFeaturesData, new HashMap<ECountries, Boolean>());

        for (int i = 0; i < ECountries.values().length; i++) {
            features.get(ECoreFeature.czyFeaturesInst).put(ECountries.values()[i], false);
            features.get(ECoreFeature.czyFeaturesSto).put(ECountries.values()[i], false);
//            features.get(ECoreFeature.czyFeaturesData).put(ECountries.values()[i], false);
        }
    }

    private void calculateFeaturesNumbers() {
        featuresNumbers = features.size();
    }
}
