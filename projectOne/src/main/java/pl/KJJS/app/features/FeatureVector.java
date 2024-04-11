package pl.KJJS.app.features;

import java.util.ArrayList;
import java.util.List;

public class FeatureVector implements MultiFeature{
//    private LiczFeatures liczFeatures = new LiczFeatures();
//    private IleFeatures ileFeatures = new IleFeatures();
//    private CzyFeatures czyFeatures = new CzyFeatures();
//    private TekstFeatures tekstFeatures = new TekstFeatures();


    @Override
    public Boolean[][] getLogicFeatures() {
        return new Boolean[0][];
    }

    @Override
    public Double[] getNumericFeatures() {
        return new Double[0];
    }

    @Override
    public String[] getTextFeatures() {
        return new String[0];
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
