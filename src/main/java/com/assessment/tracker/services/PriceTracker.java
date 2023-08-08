package com.assessment.tracker.services;

import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.Currency;
import com.assessment.tracker.models.currency.CurrencyType;

public interface PriceTracker<Request extends HistoricalPriceRequest, Response extends HistoricalPriceResponse> {
    Response fetchHistoricalPriceData(Request request);

    Currency getCurrency();

    CurrencyType getCurrencyType();

}
