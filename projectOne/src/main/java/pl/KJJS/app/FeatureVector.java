package pl.KJJS.app;

public class FeatureVector implements MultiFeature{
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
}
