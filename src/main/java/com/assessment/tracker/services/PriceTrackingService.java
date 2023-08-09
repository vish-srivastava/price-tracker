package com.assessment.tracker.services;

import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.services.factories.PriceTrackingFactory;
import org.springframework.stereotype.Service;

@Service
public class PriceTrackingService {

    private final PriceTrackingFactory priceTrackingFactory;

    public PriceTrackingService(PriceTrackingFactory priceTrackingFactory) {
        this.priceTrackingFactory = priceTrackingFactory;
    }

    /**
     * Use factory to fetch corresponding price tracker, and then fetch historical data.
     *
     * @param request : DTO which contains currency type, currency ,start and end date.
     * @return historical prices.
     */
    public HistoricalPriceResponse fetchHistoricalData(HistoricalPriceRequest request) {
        return priceTrackingFactory.fetchPriceTracker(request.getCurrency(), request.getCurrencyType()).fetchHistoricalPriceData(request);
    }
}
