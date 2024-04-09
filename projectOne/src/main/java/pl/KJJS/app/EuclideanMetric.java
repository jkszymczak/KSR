package pl.KJJS.app;

public class EuclideanMetric implements Metric {

    public double calculateLogic(Boolean[][] v1,Boolean[][] v2){
        double result =0;
        for (int i = 0; i < v1.length; i++) {
            result+=Math.pow(HammingDistance.calculateDistance(v1[i],v2[i]),2);
        }
        return result;
    }
    public double calculateText(String[] v1,String[] v2){
        double result =0;
        for (int i = 0; i < v1.length; i++) {
            result+=Math.pow(NGrams.calculateDistance(v1[i],v2[i]),2);
        }
        return result;
    }
    public double calculateNumeric(Double[] v1,Double[] v2){
        double result =0;
        for (int i = 0; i < v1.length; i++) {
            result+=Math.pow((v1[i]-v2[i]),2);
        }
        return result;
    }
    @Override
    public double calculateMetric(MultiFeature v1, MultiFeature v2) {
        Boolean[][] firstLogicFeatures = v1.getLogicFeatures();
        Boolean[][] secondLogicFeatures = v2.getLogicFeatures();
        Double[] firstNumericFeatures = v1.getNumericFeatures();
        Double[] secondNumericFeatures = v2.getNumericFeatures();
        String[] firstTextFeatures = v1.getTextFeatures();
        String[] secondTextFeatures = v2.getTextFeatures();

        Double logic = calculateLogic(firstLogicFeatures,secondLogicFeatures);
        Double text = calculateText(firstTextFeatures,secondTextFeatures);
        Double numeric = calculateNumeric(firstNumericFeatures,secondNumericFeatures);

        return Math.sqrt(numeric+logic+text);
    }
}
