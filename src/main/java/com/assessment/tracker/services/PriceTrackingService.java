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
    public HistoricalPriceResponse fetchHistoricalData(HistoricalPriceRequest request) {
        PriceTracker priceTracker = priceTrackingFactory.fetchPriceTracker(request.getCurrency(), request.getCurrencyType());
        return priceTracker.fetchHistoricalPriceData(request);
    }
}
