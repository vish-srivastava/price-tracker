package com.assessment.tracker.clients.coindesk.models;

import lombok.Data;

@Data
public class CurrencyConversionResponse {
    private String from;
    private String to;
    private double amount;
    private double conversion;
}