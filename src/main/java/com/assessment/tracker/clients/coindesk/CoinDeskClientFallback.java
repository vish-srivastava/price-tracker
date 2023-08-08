package com.assessment.tracker.clients.coindesk;

import org.springframework.stereotype.Component;

@Component
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
    public Object getHistoricalBitcoinPriceData(String start, String end, String currency) {
        throw new RuntimeException("CoinDesk API unavailable : Fallack triggered");
    }

    @Override
    public Object convertCurrency(String from, String to, double amount) {
        throw new RuntimeException("CoinDesk API unavailable : Fallack triggered");
    }
}
