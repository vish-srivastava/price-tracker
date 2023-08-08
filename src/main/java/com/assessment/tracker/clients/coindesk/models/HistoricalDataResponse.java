package com.assessment.tracker.clients.coindesk.models;

import lombok.Data;

import java.util.Map;

@Data
public class HistoricalDataResponse {
    private Map<String, Double> bpi;
}