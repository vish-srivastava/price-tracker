package com.assessment.tracker.services.factories;

import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.services.PriceTracker;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PriceTrackingFactory {
    private final List<PriceTracker> allPriceTrackers;

    @Autowired
    public PriceTrackingFactory(List<PriceTracker> currencyPriceTrackers) {
        this.allPriceTrackers = currencyPriceTrackers;
    }

    public Map<String, PriceTracker> fetchPriceTrackers(CurrencyType currencyType) {
        return allPriceTrackers.stream().filter(priceTracker -> priceTracker.getCurrencyType().equals(currencyType))
                .collect(Collectors.toMap(PriceTracker::getCurrency, Function.identity()));
    }

    public PriceTracker fetchPriceTracker(String currency, CurrencyType currencyType) {
        Map<String, PriceTracker> trackers = fetchPriceTrackers(currencyType);
        if (CollectionUtils.isEmpty(trackers)) {
            throw new NotImplementedException("No Implementations found : " + currencyType + " Currency Type not supported yet");
        }
        PriceTracker tracker = trackers.get(currency);
        if (Objects.isNull(tracker)) {
            throw new NotImplementedException("No Implementations found : " + currency + " Currency not supported yet");
        }
        return tracker;
    }
}
