package com.assessment.tracker.clients.coindesk;

import com.assessment.tracker.clients.coindesk.models.HistoricalDataResponse;
import org.springframework.stereotype.Component;

import java.net.http.HttpConnectTimeoutException;

//@Component
public class CoinDeskClientFallback {
//        implements CoinDeskClient {

    /**
     * placeholder to implement a fallback for CoinDesk for Circuit Breaking
     *
     * @param start    : in YYYY-MM-DD format.
     * @param end      : in YYYY-MM-DD format.
     * @param currency : target currency for conversion.
     * @return
     */
//    @Override
    public HistoricalDataResponse getHistoricalBitcoinPriceData(String start, String end, String currency) throws HttpConnectTimeoutException {
        throw new HttpConnectTimeoutException("CoinDesk API unavailable : Fallack triggered");
    }

    //    @Override
    public Object convertCurrency(String from, String to, double amount) {
        throw new RuntimeException("CoinDesk API unavailable : Fallack triggered");
    }
}
