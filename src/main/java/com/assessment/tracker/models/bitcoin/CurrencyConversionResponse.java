package com.assessment.tracker.models.bitcoin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionResponse {
    private String from;
    private String to;
    private double amount;
    private double conversion;
}