package pl.KJJS.app.metrics;

import pl.KJJS.app.features.MultiFeature;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ChebyshevMetric implements Metric {
    public List<Double> calculateLogic(Boolean[][] v1,Boolean[][] v2){
        List<Double> result = new LinkedList<>();
        for (int i = 0; i < v1.length; i++) {
            result.add((double) Math.abs(HammingDistance.calculateDistance(v1[i],v2[i])));
        }
        int[] x = {};
        return result;
    }
    public List<Double> calculateText(String[] v1, String[] v2){
        List<Double> result = new LinkedList<>();
        for (int i = 0; i < v1.length; i++) {
            result.add(Math.abs(NGrams.calculateDistance(v1[i],v2[i])));
        }
        return result;
    }
    public List<Double> calculateNumeric(Double[] v1,Double[] v2){
        List<Double> result = new LinkedList<>();
        for (int i = 0; i < v1.length; i++) {
            result.add(Math.abs((v1[i]-v2[i])));
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

        List<Double> logic = calculateLogic(firstLogicFeatures,secondLogicFeatures);
        List<Double> text = calculateText(firstTextFeatures,secondTextFeatures);
        List<Double> numeric = calculateNumeric(firstNumericFeatures,secondNumericFeatures);
        List<Double> values = new LinkedList<>();
        values.addAll(logic);
        values.addAll(text);
        values.addAll(numeric);
        return Collections.max(values);
    }
}
