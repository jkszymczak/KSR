package pl.KJJS.app.metrics;

import pl.KJJS.app.features.MultiFeature;

public interface Metric {
    double calculateMetric(MultiFeature v1, MultiFeature v2);
}
