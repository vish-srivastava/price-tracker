package com.assessment.tracker.controllers;

import com.assessment.tracker.CommonTestUtils;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.bitcoin.HistoricalDataResponse;
import com.assessment.tracker.models.currency.AvailableCurrencyResponse;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import com.assessment.tracker.services.PriceTrackingService;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpConnectTimeoutException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static com.assessment.tracker.CommonTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PriceTrackingControllerTest {

    @Mock
    private PriceTrackingService priceTrackingService;

    private PriceTrackingController priceTrackingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceTrackingController = new PriceTrackingController(priceTrackingService);
    }

    @Test
    void testFetchHistoricalData_Success() throws HttpConnectTimeoutException {
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, Crypto.BTC.getCurrencyAbbreviation());
        HistoricalDataResponse dataResponse = CommonTestUtils.getResponse();
        HistoricalPriceResponse response = HistoricalPriceResponse.builder().response(dataResponse).build();

        when(priceTrackingService.fetchHistoricalData(request))
                .thenReturn(response);

        ResponseEntity<?> actualResponse = priceTrackingController.fetchHistoricalData(request);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertTrue(actualResponse.getBody() instanceof HistoricalPriceResponse);
    }

    @Test
    void testFetchHistoricalData_NotImplementedException() throws HttpConnectTimeoutException {
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, Crypto.BTC.getCurrencyAbbreviation());
        when(priceTrackingService.fetchHistoricalData(request))
                .thenThrow(NotImplementedException.class);
        ResponseEntity<?> actualResponse = priceTrackingController.fetchHistoricalData(request);
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testFetchAvailableCryptoCurrencies_Success() {
        // Mocking the service to return a list of available currencies
        when(priceTrackingService.getAvailableCurrencies()).thenReturn(Optional.of(new AvailableCurrencyResponse(Collections.emptyList())));

        ResponseEntity<?> response = priceTrackingController.fetchAvailableCryptoCurrencies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Add more assertions as needed to verify the response body
    }

    @Test
    public void testFetchAvailableCryptoCurrencies_NotImplementedException() {
        // Mocking the service to throw a NotImplementedException
        when(priceTrackingService.getAvailableCurrencies()).thenThrow(NotImplementedException.class);

        ResponseEntity<?> response = priceTrackingController.fetchAvailableCryptoCurrencies();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        // Add assertions to check the response body and error message
    }

    @Test
    public void testFetchAvailableCryptoCurrencies_RuntimeException() {
        // Mocking the service to throw a RuntimeException
        when(priceTrackingService.getAvailableCurrencies()).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = priceTrackingController.fetchAvailableCryptoCurrencies();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNull(response.getBody());
        // Add assertions to check the response body and error message
    }

    @Test
    void testFetchHistoricalData_RuntimeException() throws HttpConnectTimeoutException {
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, Crypto.BTC.getCurrencyAbbreviation());
        when(priceTrackingService.fetchHistoricalData(request)).thenThrow(RuntimeException.class);
        ResponseEntity<?> actualResponse = priceTrackingController.fetchHistoricalData(request);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, actualResponse.getStatusCode());
    }

    private HistoricalPriceRequest getRequest(CurrencyType currencyType, String currency) {
        return HistoricalPriceRequest.builder()
                .startDate(LocalDate.parse(startDate, dateTimeFormatter))
                .endDate(LocalDate.parse(endDate, dateTimeFormatter))
                .currencyType(currencyType)
                .currency(currency)
                .targetCurrency(Federal.USD.getCurrencyAbbreviation())
                .build();
    }
}
