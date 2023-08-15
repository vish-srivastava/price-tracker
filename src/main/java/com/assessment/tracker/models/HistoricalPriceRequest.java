package com.assessment.tracker.models;

import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import lombok.*;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@Builder
public class HistoricalPriceRequest {
    private CurrencyType currencyType;
    private String currency;
    private String targetCurrency = Federal.USD.getCurrencyAbbreviation(); // default value
    private LocalDate startDate;
    private LocalDate endDate;
}
