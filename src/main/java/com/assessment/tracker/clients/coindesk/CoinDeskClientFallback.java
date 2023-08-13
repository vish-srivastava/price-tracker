package com.assessment.tracker.clients.coindesk;

import java.net.http.HttpConnectTimeoutException;

//@Component
public class CoinDeskClientFallback implements CoinDeskClient {

    /**
     * placeholder to implement a fallback for CoinDesk for Circuit Breaking
     *
     * @param start    : in YYYY-MM-DD format.
     * @param end      : in YYYY-MM-DD format.
     * @param currency : target currency for conversion.
     * @return
     */
    @Override
    public String getHistoricalBitcoinPriceData(String start, String end, String currency) throws HttpConnectTimeoutException {
        throw new HttpConnectTimeoutException("CoinDesk API unavailable : Fallack triggered");
    }

    @Override
    public String convertCurrency(String from, String to, double amount) {
        throw new RuntimeException("CoinDesk API unavailable : Fallack triggered");
    }

    @Override
    public String getSupportedCurrencies() {
        throw new RuntimeException("CoinDesk API unavailable : Fallack triggered");
    }
}
