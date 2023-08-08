package com.assessment.tracker.models.currency;

import java.util.Optional;

public enum Federal implements Currency {

    INR("Indian Rupee", "INR", "₹"),
    USD("United States Dollar", "USD", "$");
    private final String displayName;
    private final String value;

    private final String symbol;

    Federal(String displayName, String value, String symbol) {
        this.displayName = displayName;
        this.value = value;
        this.symbol = symbol;
    }

    @Override
    public String getCurrencyAbbreviation() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Optional<String> getSymbol() {
        return Optional.ofNullable(symbol);
    }
}
