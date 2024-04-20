package pl.KJJS.app.features;

import pl.KJJS.app.parser.Keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class IleFeatures implements Serializable {
    private static class ComplexFeaturesType extends HashMap<ECoreFeature, HashMap<ECountries, Double>> {
    }

    private static class SimpleFeaturesType extends HashMap<ECoreFeature, Double> {
    }

    public final static int eCoreFeaturesBias = 4;
    private static int featuresNumbers;
    private ComplexFeaturesType complexFeatures = new ComplexFeaturesType();
    private SimpleFeaturesType simpleFeatures = new SimpleFeaturesType();

    public IleFeatures() {
        initVariables();
    }

    public static int getFeaturesNumbers() {
        return featuresNumbers;
    }

    public HashMap<ECoreFeature, HashMap<ECountries, Double>> getComplexFeatures() {
        return this.complexFeatures;
    }

    public HashMap<ECoreFeature, Double> getSimpleFeatures() {
        return this.simpleFeatures;
    }

    public Double[] getFeaturesAsVector() {
        List<Double> featuresVector = new ArrayList<>(featuresNumbers);

        int coreFeaturesLength;
        int i;
        for (i = 0; i < complexFeatures.size(); i++) {
            coreFeaturesLength = complexFeatures.get(ECoreFeature.values()[i + eCoreFeaturesBias]).size();
            for (int j = 0; j < coreFeaturesLength; j++) {
                featuresVector.add(complexFeatures.
                        get(ECoreFeature.values()[i + eCoreFeaturesBias]).get(ECountries.values()[j]));
            }
        }
        for (int k = 0; k < simpleFeatures.size(); k++) {
            featuresVector.add(simpleFeatures.get(ECoreFeature.values()[k + i + eCoreFeaturesBias]));
        }

        return featuresVector.toArray(new Double[featuresNumbers]);
    }

    public Double getFeature(ECoreFeature coreFeature, ECountries country) {
        if (country == null) {
            return this.simpleFeatures.get(coreFeature);
        } else {
            return this.complexFeatures.get(coreFeature).get(country);
        }
    }

    public void calculateFeatures(HashMap<Keys, HashMap<ECountries, String[][]>> dicts, List<Keys> keys, String[] text) {
        double featureValue;
        List<String> textAsArray = new ArrayList<String>(List.of(text));
        int i;
        for (i = 0; i < complexFeatures.size(); i++) {
            for (int j = 0; j < complexFeatures.get(ECoreFeature.values()[i + eCoreFeaturesBias]).size(); j++) {
                featureValue = calculateSingleFeature(dicts.get(keys.get(i)).get(ECountries.values()[j]), textAsArray);
                featureValue = normalize(featureValue, 0.0, (double) textAsArray.size() / 2);
                complexFeatures.get(ECoreFeature.values()[i + eCoreFeaturesBias]).put(ECountries.values()[j], featureValue);
            }
        }

        // TODO temporary hardcode continents dict, to delete
        String[][] tempContinentsDict = {
                {"asia"},
                {"europe"},
                {"north", "america"}};

        for (int k = 0; k < simpleFeatures.size(); k++) {
            if (ECoreFeature.values()[k + i + eCoreFeaturesBias] == ECoreFeature.ileFeaturesNajKont) {
                featureValue = calculateMostCommonContinentsFeature(tempContinentsDict, textAsArray);
                featureValue = normalize(featureValue, 0.0, (double) textAsArray.size() / 4);
                simpleFeatures.put(ECoreFeature.values()[k + i + eCoreFeaturesBias], featureValue);
            }
//            else if (ECoreFeature.values()[k + i + eCoreFeaturesBias] == ECoreFeature.ileFeaturesDlTekst) {
//                featureValue = textAsArray.size();
//                featureValue = normalize(featureValue, 1.0, textAsArray.size());
//                simpleFeatures.put(ECoreFeature.values()[k + i + eCoreFeaturesBias], featureValue);
//            }
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
                    if (i + patternLengthCounter >= textLength) {
                        break;
                    }
                    if (!row[j].equals(text.get(i + patternLengthCounter))) {
                        break;
                    }
                    patternLengthCounter++;
                }
                if (patternLengthCounter == row.length) {
                    featureValue += row.length;
                }
            }
        }
        return featureValue;
    }

    private double calculateMostCommonContinentsFeature(String[][] dict, List<String> text) {
        int textLength = text.size();
        List<Double> featureValue = new ArrayList<>();
        int patternLengthCounter;

        for (int i = 0; i < dict.length; i++) {
            featureValue.add(0.0);
        }

        int k;
        for (int i = 0; i < textLength; i++) {
            k=0;
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
                    featureValue.set(k, featureValue.get(k) + 1.0);
                }
                k++;
            }
        }
        return Collections.max(featureValue);
    }

    private double normalize(double featureValue, double minValue, double maxValue) {
        return (featureValue - minValue) / (maxValue - minValue);
    }

    private void initVariables() {
        initFeaturesValues();
        calculateFeaturesNumbers();
    }

    private void initFeaturesValues() {
        complexFeatures.put(ECoreFeature.ileFeaturesKlucz, new HashMap<ECountries, Double>());
        for (int i = 0; i < ECountries.values().length; i++) {
            complexFeatures.get(ECoreFeature.ileFeaturesKlucz).put(ECountries.values()[i], 0.0);

        }

        simpleFeatures.put(ECoreFeature.ileFeaturesNajKont, 0.0);
//        simpleFeatures.put(ECoreFeature.ileFeaturesDlTekst, 0.0);
    }

    private void calculateFeaturesNumbers() {
        featuresNumbers = 0;
        for (int i = 0; i < complexFeatures.size(); i++) {
            for (int j = 0; j < complexFeatures.get(ECoreFeature.values()[i + eCoreFeaturesBias]).size(); j++) {
                featuresNumbers++;
            }
        }
        for (int i = 0; i < simpleFeatures.size(); i++) {
            featuresNumbers++;
        }
    }
}
