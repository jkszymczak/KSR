package pl.KJJS.app.features;

public interface MultiFeature {
    String[] getTextFeatures();
    Boolean[][] getLogicFeatures();
    Double[] getNumericFeatures();
}
