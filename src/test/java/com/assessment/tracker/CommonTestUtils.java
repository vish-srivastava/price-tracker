package com.assessment.tracker;

import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.bitcoin.HistoricalDataResponse;
import com.assessment.tracker.models.currency.CurrencyType;
import com.assessment.tracker.models.currency.Federal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonTestUtils {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String startDate = "2022-01-01";
    public static final String endDate = "2022-02-01";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getSuccessResponse() {
        return "{\"bpi\":{\"2022-01-01\":47742.995,\"2022-01-02\":47310.9233,\"2022-01-03\":46448.0367,\"2022-01-04\":45837.3333,\"2022-01-05\":43430.7067,\"2022-01-06\":43098.685,\"2022-01-07\":41541.2233,\"2022-01-08\":41689.195,\"2022-01-09\":41864.1067,\"2022-01-10\":41838.4617,\"2022-01-11\":42744.29,\"2022-01-12\":43921.2833,\"2022-01-13\":42577.1583,\"2022-01-14\":43095.525,\"2022-01-15\":43096.105,\"2022-01-16\":43091.2567,\"2022-01-17\":42224.7167,\"2022-01-18\":42373.6817,\"2022-01-19\":41676.665,\"2022-01-20\":40705.4417,\"2022-01-21\":36462.6283,\"2022-01-22\":35085.14,\"2022-01-23\":36286.2217,\"2022-01-24\":36703.22,\"2022-01-25\":36981.4917,\"2022-01-26\":36828.1,\"2022-01-27\":37194.41,\"2022-01-28\":37740.7367,\"2022-01-29\":38186.37,\"2022-01-30\":37915.8983,\"2022-01-31\":38499.2683,\"2022-02-01\":38718.8067},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.\",\"time\":{\"updated\":\"Feb 2, 2022 00:03:00 UTC\",\"updatedISO\":\"2022-02-02T00:03:00+00:00\"}}";
    }

    public static HistoricalDataResponse getResponse() {
        try {
            return objectMapper.readValue(getSuccessResponse(), HistoricalDataResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static HistoricalPriceRequest getRequest(CurrencyType currencyType, String currency) {
        return HistoricalPriceRequest.builder()
                .startDate(LocalDate.parse(startDate, dateTimeFormatter))
                .endDate(LocalDate.parse(endDate, dateTimeFormatter))
                .currencyType(currencyType)
                .currency(currency)
                .targetCurrency(Federal.USD.getCurrencyAbbreviation())
                .build();
    }
}
