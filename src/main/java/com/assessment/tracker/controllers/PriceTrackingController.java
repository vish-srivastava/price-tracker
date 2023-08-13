package com.assessment.tracker.controllers;

import com.assessment.tracker.models.AvailableCurrencyResponse;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import com.assessment.tracker.services.PriceTrackingService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpConnectTimeoutException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@RestController
public class PriceTrackingController {

    private final PriceTrackingService priceTrackingService;

    public PriceTrackingController(PriceTrackingService priceTrackingService) {
        this.priceTrackingService = priceTrackingService;
    }

    /**
     * fetches historical price data for a data range and currency
     *
     * @param historicalPriceRequest : request DTO for price tracking
     * @return
     */
    @PostMapping("/historical")
    public ResponseEntity<?> fetchHistoricalData(@RequestBody HistoricalPriceRequest historicalPriceRequest) {
        try {
            Optional<HistoricalPriceResponse> response = Optional.ofNullable(priceTrackingService.fetchHistoricalData(historicalPriceRequest));
            return response.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (NotImplementedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException | HttpConnectTimeoutException runtimeException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(runtimeException.getMessage());
        }

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


}

