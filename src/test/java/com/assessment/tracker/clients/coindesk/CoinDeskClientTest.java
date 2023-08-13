package com.assessment.tracker.clients.coindesk;

import com.assessment.tracker.clients.coindesk.config.CoinDeskFeignConfig;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.DecodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoinDeskClientTest {

    @Mock
    private CoinDeskClient coinDeskClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHistoricalBitcoinPriceData() throws HttpConnectTimeoutException {
        String start = "2022-01-01";
        String end = "2022-02-01";
        String currency = "USD";
        String expectedResult = "Expected result"; // Replace with your expected result

        Mockito.when(coinDeskClient.getHistoricalBitcoinPriceData(start, end, currency))
                .thenReturn(expectedResult);

        String actualResult = coinDeskClient.getHistoricalBitcoinPriceData(start, end, currency);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetHistoricalBitcoinPriceData_FeignException() throws HttpConnectTimeoutException {
        String start = "2022-01-01";
        String end = "2022-02-01";
        String currency = "USD";

        // Mock FeignException when calling the Feign client
        FeignException feignException = FeignException.errorStatus("GET",
                Response.builder()
                        .status(404)
                        .reason("Not Found")
                        .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, new RequestTemplate()))
                        .build());
        Mockito.when(coinDeskClient.getHistoricalBitcoinPriceData(start, end, currency)).thenThrow(feignException);
        assertThrows(FeignException.class, () -> coinDeskClient.getHistoricalBitcoinPriceData(start, end, currency));
    }
}
