package com.assessment.tracker.services;

import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.CurrencyType;

public interface PriceTracker {
    HistoricalPriceResponse fetchHistoricalPriceData(HistoricalPriceRequest request);

    String getCurrency();

    CurrencyType getCurrencyType();

}
