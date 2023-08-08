package com.assessment.tracker.models;

import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoricalPriceResponse {
    private LocalDate startDate;
    private LocalDate endDate;
}
