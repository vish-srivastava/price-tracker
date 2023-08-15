package com.assessment.tracker.services.implementations;

import com.assessment.tracker.clients.coindesk.CoinDeskClient;
import com.assessment.tracker.models.currency.AvailableCurrencyResponse;
import com.assessment.tracker.models.currency.CurrencyResponse;
import com.assessment.tracker.services.PriceTracker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractPriceTracker implements PriceTracker {
    private final CoinDeskClient coinDeskClient;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected AbstractPriceTracker(CoinDeskClient coinDeskClient) {
        this.coinDeskClient = coinDeskClient;
    }

    @Override
    @Cacheable(value = "getSupportedCurrencies", unless = "#result == null")
    public AvailableCurrencyResponse fetchAvailableCurrencies() {
        String response = coinDeskClient.getSupportedCurrencies();
        try {
            List<CurrencyResponse> currencyResponseList = objectMapper.readValue(response, new TypeReference<List<CurrencyResponse>>() {
            });
            return new AvailableCurrencyResponse(currencyResponseList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse json response from client : unexpected response " + e);
        }
    }

}
