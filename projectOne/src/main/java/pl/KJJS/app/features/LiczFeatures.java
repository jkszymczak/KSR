package pl.KJJS.app.features;

import java.util.ArrayList;
import java.util.List;

public class LiczFeatures {
    private static int featuresNumbers;
    // If you want to change features, you have to adapt <corefeatures> and <howManyEachFeatures> the class each time.
    private List<List<Double>> allLiczFeatures = new ArrayList<>();
    private Integer coreFeatures = 4;
    private List<Integer> howManyEachFeatures = new ArrayList<>(); // TODO set default this value {6, 6, 6, 6}

    public LiczFeatures() {
//        for (int i = 0; i < coreFeatures.size(); i++) {
//            allLiczFeatures.add();
//        }

        featuresNumbers = calculateFeaturesNumbers();
    }

    public static int getFeaturesNumbers() {
        return featuresNumbers;
    }

    public static int calculateFeaturesNumbers() throws IllegalArgumentException {
        // TODO calculate features numbers
        return 0;
    }

//    public List<Double> getFeatures() {
//        return this.features;
//    }
//
//    public Double getFeature(int index) {
//        return this.features.get(index);
//    }
//
//    public void calculateFeatures() {
//        for (int i = 0; i < featuresNumbers; i++) {
//            this.features.add(calculateSingleFeature(i));
//        }
//    }
//
//    private Double calculateSingleFeature(int index) {
//        return 0.0;
//    }

    /*
    Nr.     - Feature name
    0-5     - liczFeaturesGeo
    6-11    - liczFeaturesObi
    12-17   - liczFeaturesMiast
    18-23   - liczFeaturesOsob
     */
}
