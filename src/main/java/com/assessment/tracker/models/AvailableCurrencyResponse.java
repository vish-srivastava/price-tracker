package com.assessment.tracker.models;

import com.assessment.tracker.models.currency.Crypto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class AvailableCurrencyResponse {
    private Map<String, String> availableCurrencies;
}
