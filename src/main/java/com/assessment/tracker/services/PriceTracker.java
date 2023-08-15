package com.assessment.tracker.services;

import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.AvailableCurrencyResponse;
import com.assessment.tracker.models.currency.CurrencyType;

import java.net.http.HttpConnectTimeoutException;

public interface PriceTracker {
    HistoricalPriceResponse fetchHistoricalPriceData(HistoricalPriceRequest request) throws HttpConnectTimeoutException;

    String getCurrency();

    CurrencyType getCurrencyType();

    AvailableCurrencyResponse fetchAvailableCurrencies();

    boolean isDefault();

}
