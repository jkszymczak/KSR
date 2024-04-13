package pl.KJJS.app.quality;

import pl.KJJS.app.features.ECountries;

public interface Label {
    /**
     * Get correct class of Article
     */
    public ECountries getExpected();
    /**
     * Get result of classification*/
    public ECountries getResult();
}
