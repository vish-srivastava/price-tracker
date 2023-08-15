package com.assessment.tracker.models.currency;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class AvailableCurrencyResponse {
    private List<CurrencyResponse> data;
}
