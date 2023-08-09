package com.assessment.tracker.models.bitcoin;

import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoinPriceRequest extends HistoricalPriceRequest {
    private CurrencyType currencyType = CurrencyType.CRYPTO;     // default value
    private String currency = Crypto.BTC.getCurrencyAbbreviation();    // default value


    @Override
    public String getCurrency() {
        return this.currency;
    }

    @Override
    public CurrencyType getCurrencyType() {
        return this.currencyType;
    }
}
