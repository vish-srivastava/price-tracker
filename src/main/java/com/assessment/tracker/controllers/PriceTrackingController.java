package com.assessment.tracker.controllers;


import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.services.PriceTrackingService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpConnectTimeoutException;
import java.util.Optional;


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
    @PostMapping(value = "/historical", produces = MediaType.APPLICATION_JSON_VALUE)
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
     * @return : list of available currencies and their countries in the system
     */
    @GetMapping(value = "/available-currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAvailableCryptoCurrencies() {
        try {
            return priceTrackingService.getAvailableCurrencies().map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (NotImplementedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(runtimeException.getMessage());
        }
    }


}

