package com.assessment.tracker.services.implementations;

import com.assessment.tracker.clients.coindesk.CoinDeskClient;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.services.PriceTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitcoinPriceTracker implements PriceTracker {
    private final CoinDeskClient coinDeskClient;

    @Autowired
    public BitcoinPriceTracker(CoinDeskClient coinDeskClient) {
        this.coinDeskClient = coinDeskClient;
    }

    @Override
    public HistoricalPriceResponse fetchHistoricalPriceData(HistoricalPriceRequest request) {
        coinDeskClient.getHistoricalBitcoinPriceData(null, null, null);
        return null;
    }

    @Override
    public Crypto getCurrency() {
        return Crypto.BTC;
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.CRYPTO;
    }
}
