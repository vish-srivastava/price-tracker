package com.assessment.tracker.clients.coindesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalDataResponse {
    private Map<String, Double> bpi;
}