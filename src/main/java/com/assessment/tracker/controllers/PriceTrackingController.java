package com.assessment.tracker.controllers;

import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.AvailableCurrencyResponse;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import com.assessment.tracker.services.PriceTrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class PriceTrackingController {

    private final PriceTrackingService priceTrackingService;

    public PriceTrackingController(PriceTrackingService priceTrackingService) {
        this.priceTrackingService = priceTrackingService;
    }

    /**
     * Get list of available currencies in system .
     *
     * @param currencyType : FEDERAL or CRYPTO
     * @return : list of available currencies in the system
     */
    @GetMapping("/available-currencies")
    public ResponseEntity<AvailableCurrencyResponse> fetchAvailableCryptoCurrencies(
            @RequestParam(value = "currencyType", defaultValue = "CRYPTO") CurrencyType currencyType) {
        Map<String, String> availableCryptoCurrencies;
        if (currencyType.equals(CurrencyType.CRYPTO)) {
            availableCryptoCurrencies = Arrays.stream(Crypto.values())
                    .collect(Collectors.toMap(Crypto::getCurrencyAbbreviation, Crypto::getDisplayName));
        } else {
            availableCryptoCurrencies = Arrays.stream(Federal.values())
                    .collect(Collectors.toMap(Federal::getCurrencyAbbreviation, Federal::getDisplayName));
        }
        return ResponseEntity.ok(new AvailableCurrencyResponse(availableCryptoCurrencies));
    }

    /**
     * fetches historical price data for a data range and currency
     *
     * @param historicalPriceRequest
     * @return
     */
    @PostMapping("/historical")
    public ResponseEntity<?> fetchHistoricalData(HistoricalPriceRequest historicalPriceRequest) {
        Optional<HistoricalPriceResponse> response = Optional.ofNullable(priceTrackingService.fetchHistoricalData(historicalPriceRequest));
        return response.map(data -> ResponseEntity.ok(data)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

