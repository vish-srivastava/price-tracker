package com.assessment.tracker.clients.coindesk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpConnectTimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CoinDeskClientFallbackTest {

    private CoinDeskClientFallback coinDeskClientFallback;

    @BeforeEach
    void setUp() {
        coinDeskClientFallback = new CoinDeskClientFallback();
    }

    @Test
    void testGetHistoricalBitcoinPriceData() {
        String start = "2022-01-01";
        String end = "2022-02-01";
        String currency = "USD";

        assertThrows(HttpConnectTimeoutException.class,
                () -> coinDeskClientFallback.getHistoricalBitcoinPriceData(start, end, currency));
    }

    @Test
    void testConvertCurrency() {
        String from = "USD";
        String to = "EUR";
        double amount = 100.0;

        assertThrows(RuntimeException.class,
                () -> coinDeskClientFallback.convertCurrency(from, to, amount));
    }
}
