package com.assessment.tracker.services;

import com.assessment.tracker.clients.coindesk.CoinDeskClient;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.Currency;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import com.assessment.tracker.services.factories.PriceTrackingFactory;
import com.assessment.tracker.services.implementations.BitcoinPriceTracker;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpConnectTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PriceTrackingServiceTest {
    @Mock
    private CoinDeskClient coinDeskClient;
    @InjectMocks
    private BitcoinPriceTracker bitcoinPriceTracker;
    private PriceTrackingFactory priceTrackingFactory;
    private final String startDate = "2022-01-01";
    private final String endDate = "2022-02-01";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @BeforeEach
    private void setup() throws JsonProcessingException, HttpConnectTimeoutException {
        priceTrackingFactory = new PriceTrackingFactory(List.of(bitcoinPriceTracker));
        when(coinDeskClient.getHistoricalBitcoinPriceData(any(), any(), any())).thenReturn(getSuccessResponse());

    }

    @Test
    void testPriceTrackingFactory() {
        CurrencyType currencyType = CurrencyType.CRYPTO;
        Currency currency = Crypto.BTC;
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, currency.getCurrencyAbbreviation());
        PriceTracker tracker = priceTrackingFactory.fetchPriceTracker(currency.getCurrencyAbbreviation(), currencyType);
        assertTrue(tracker instanceof BitcoinPriceTracker);
    }

    @Test
    void testPriceTrackingFactoryBadRequest() {
        CurrencyType currencyType = CurrencyType.CRYPTO;
        Currency currency = Crypto.ETH;
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, currency.getCurrencyAbbreviation());
        try {
            priceTrackingFactory.fetchPriceTracker(currency.getCurrencyAbbreviation(), currencyType);
        } catch (Exception e) {
            assertTrue(e instanceof NotImplementedException);
            assertTrue(e.getMessage().contains("No Implementations found "));
        }
    }

    @Test
    void testHappyCase() throws HttpConnectTimeoutException {
        CurrencyType currencyType = CurrencyType.CRYPTO;
        Currency currency = Crypto.BTC;
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, currency.getCurrencyAbbreviation());
        PriceTracker tracker = priceTrackingFactory.fetchPriceTracker(currency.getCurrencyAbbreviation(), currencyType);
        HistoricalPriceResponse response = tracker.fetchHistoricalPriceData(request);
        assertTrue(Objects.nonNull(response));
        assertTrue(response.getCurrencyType().equals(currencyType));
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

    private String getSuccessResponse() {
        return "{\"bpi\":{\"2022-01-01\":47742.995,\"2022-01-02\":47310.9233,\"2022-01-03\":46448.0367,\"2022-01-04\":45837.3333,\"2022-01-05\":43430.7067,\"2022-01-06\":43098.685,\"2022-01-07\":41541.2233,\"2022-01-08\":41689.195,\"2022-01-09\":41864.1067,\"2022-01-10\":41838.4617,\"2022-01-11\":42744.29,\"2022-01-12\":43921.2833,\"2022-01-13\":42577.1583,\"2022-01-14\":43095.525,\"2022-01-15\":43096.105,\"2022-01-16\":43091.2567,\"2022-01-17\":42224.7167,\"2022-01-18\":42373.6817,\"2022-01-19\":41676.665,\"2022-01-20\":40705.4417,\"2022-01-21\":36462.6283,\"2022-01-22\":35085.14,\"2022-01-23\":36286.2217,\"2022-01-24\":36703.22,\"2022-01-25\":36981.4917,\"2022-01-26\":36828.1,\"2022-01-27\":37194.41,\"2022-01-28\":37740.7367,\"2022-01-29\":38186.37,\"2022-01-30\":37915.8983,\"2022-01-31\":38499.2683,\"2022-02-01\":38718.8067},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.\",\"time\":{\"updated\":\"Feb 2, 2022 00:03:00 UTC\",\"updatedISO\":\"2022-02-02T00:03:00+00:00\"}}";
    }
}
