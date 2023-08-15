package com.assessment.tracker.services.implementations;

import com.assessment.tracker.clients.coindesk.CoinDeskClient;
import com.assessment.tracker.models.bitcoin.HistoricalDataResponse;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.Currency;
import com.assessment.tracker.models.currency.CurrencyType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.http.HttpConnectTimeoutException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class BitcoinPriceTracker extends AbstractPriceTracker {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CoinDeskClient coinDeskClient;
    private final Currency currency = Crypto.BTC;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public BitcoinPriceTracker(CoinDeskClient coinDeskClient) {
        super(coinDeskClient);
        this.coinDeskClient = coinDeskClient;
    }

    @Override
    @Cacheable(value = "fetchHistoricalPriceData", unless = "#result == null")
    public HistoricalPriceResponse fetchHistoricalPriceData(HistoricalPriceRequest request) throws HttpConnectTimeoutException {
        String response = getHistoricalBitcoinPriceData(request);
        try {
            HistoricalDataResponse dataResponse = objectMapper.readValue(response, HistoricalDataResponse.class);
            Map<String, Double> bpi = dataResponse.getBpi();
            // Find the LocalDate with maximum value
            Optional<String> maxDate = bpi.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);
            Optional<String> minDate = bpi.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);

            return HistoricalPriceResponse.builder()
                    .currencyType(getCurrencyType())
                    .currencyName(currency.getCurrencyAbbreviation())
                    .currencySymbol(currency.getSymbol().orElse(null))
                    .currencyDisplayName(currency.getDisplayName())
                    .minimaDate(minDate.orElse(null))
                    .maximaDate(maxDate.orElse(null))
                    .response(dataResponse)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse json response from client : unexpected response " + e);
        }
    }

    @Override
    public String getCurrency() {
        return currency.getCurrencyAbbreviation();
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.CRYPTO;
    }

    @Override
    public boolean isDefault() {
        return true;
    }

    private String getHistoricalBitcoinPriceData(HistoricalPriceRequest request) throws HttpConnectTimeoutException {
        return coinDeskClient.getHistoricalBitcoinPriceData(request.getStartDate().format(dateTimeFormatter), request.getEndDate().format(dateTimeFormatter), request.getTargetCurrency());
    }
}
