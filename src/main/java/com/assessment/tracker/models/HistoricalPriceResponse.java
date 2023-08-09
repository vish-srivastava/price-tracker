package com.assessment.tracker.models;

import com.assessment.tracker.clients.coindesk.models.HistoricalDataResponse;
import com.assessment.tracker.common.LocalDateSerializer;
import com.assessment.tracker.models.currency.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
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

