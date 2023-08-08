package com.assessment.tracker.models.currency;

import java.util.Optional;

public enum Crypto implements Currency {

    BTC("Bitcoin", "BTC", "₿"),
    ETH("Ethereum", "ETH", "Ξ"),
    BNB("BNB", "BNB", null),
    XRP("XRP", "XRP", null),
    DOGE("Dogecoin", "DOGE", " Ɖ");

    private final String displayName;
    private final String value;

    private final String symbol;

    Crypto(String displayName, String value, String symbol) {
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
