package pl.KJJS.app.quality;

import pl.KJJS.app.features.Countries;

public interface Label {
    /**
     * Get correct class of Article
     */
    public Countries getExpected();
    /**
     * Get result of classification*/
    public Countries getResult();
}
