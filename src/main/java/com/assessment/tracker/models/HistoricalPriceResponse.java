package com.assessment.tracker.models;

import com.assessment.tracker.models.bitcoin.HistoricalDataResponse;
import com.assessment.tracker.models.currency.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalPriceResponse {
    private CurrencyType currencyType;
    private String currencyName;
    private String currencySymbol;
    private String currencyDisplayName;
    private String minimaDate;
    private String maximaDate;
    private HistoricalDataResponse response;
}

