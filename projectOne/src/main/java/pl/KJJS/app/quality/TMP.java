package pl.KJJS.app.quality;

import pl.KJJS.app.features.ECountries;

public class TMP implements Label{

    ECountries expected;
    ECountries result;

    public TMP(ECountries e,ECountries r){
        this.expected=e;
        this.result = r;
    }

    @Override
    public ECountries getExpected() {
        return this.expected;
    }

    @Override
    public ECountries getResult() {
        return this.result;
    }
}
