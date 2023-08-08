package com.assessment.tracker.models.currency;

import java.util.Optional;

public interface Currency {
    String getCurrencyAbbreviation();

    String getDisplayName();

    Optional<String> getSymbol();

}
