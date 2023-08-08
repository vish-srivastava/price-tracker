package com.assessment.tracker.models;

import com.assessment.tracker.models.currency.Crypto;
import com.assessment.tracker.models.currency.Currency;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoricalPriceRequest {
    private CurrencyType currencyType = CurrencyType.CRYPTO;     // default value
    private Currency currency = Crypto.BTC;    // default value
    private Currency targetCurrency = Federal.USD; // default value
    private LocalDate startDate;
    private LocalDate endDate;
}
