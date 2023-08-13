package com.assessment.tracker.services.factories;

import com.assessment.tracker.clients.coindesk.CoinDeskClient;
import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.services.PriceTracker;
import com.assessment.tracker.services.implementations.BitcoinPriceTracker;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PriceTrackingFactoryTest {


    private PriceTrackingFactory priceTrackingFactory;

    @Mock
    private List<PriceTracker> allPriceTrackers;

    @Mock
    private CoinDeskClient coinDeskClient;

    @InjectMocks
    private BitcoinPriceTracker bitcoinPriceTracker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceTrackingFactory = new PriceTrackingFactory(List.of(bitcoinPriceTracker));
    }

    @Test
    void testFetchPriceTracker() {
        String currency = Crypto.BTC.getCurrencyAbbreviation();
        CurrencyType currencyType = CurrencyType.CRYPTO;
        PriceTracker expectedTracker = Mockito.mock(PriceTracker.class);
        // Mock the behavior of the PriceTracker instance
        Mockito.when(expectedTracker.getCurrencyType()).thenReturn(currencyType);
        Mockito.when(expectedTracker.getCurrency()).thenReturn(currency);
        // Mock the fetchPriceTrackers method
        PriceTracker actualTracker = priceTrackingFactory.fetchPriceTracker(currency, currencyType);
        assertInstanceOf(BitcoinPriceTracker.class, actualTracker);
    }

    @Test
    void testFetchPriceTracker_NoImplementationsFound() {
        String currency = "USD";
        CurrencyType currencyType = CurrencyType.CRYPTO;

        // Mock the behavior of the PriceTracker list
        Mockito.when(allPriceTrackers.stream()).thenReturn(new ArrayList<PriceTracker>().stream());

        assertThrows(NotImplementedException.class, () -> priceTrackingFactory.fetchPriceTracker(currency, currencyType));
    }
}
