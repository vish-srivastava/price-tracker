package com.assessment.tracker.clients.coindesk;

import com.assessment.tracker.clients.coindesk.config.CoinDeskFeignConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "coinDeskClient", url = "${coindesk.api.url}", configuration = CoinDeskFeignConfig.class)
public interface CoinDeskClient {

    /**
     * Used for fetching historical BTC Price only.
     * @param start : in YYYY-MM-DD format.
     * @param end : in YYYY-MM-DD format.
     * @param currency : target currency for conversion.
     * @return
     */
    @RequestLine("GET /v1/bpi/historical/close.json?start={start}&end={end}&currency={currency}")
    Object getHistoricalBitcoinPriceData(
            @Param("start") String start,
            @Param("end") String end,
            @Param("currency") String currency
    );

    /**
     * Used to convert one currency to another
     * @param from : in YYYY-MM-DD format.
     * @param to : in YYYY-MM-DD format.
     * @param amount : amount to be converted.
     * @return
     */

    @RequestLine("GET /v1/bpi/convert.json?from={from}&to={to}&amount={amount}")
    Object convertCurrency(
            @Param("from") String from,
            @Param("to") String to,
            @Param("amount") double amount
    );
}
