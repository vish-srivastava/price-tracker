package com.assessment.tracker.clients.coindesk;

import com.assessment.tracker.clients.coindesk.config.CoinDeskFeignConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "coinDeskClient", url = "${coindesk.api.url}", configuration = CoinDeskFeignConfig.class)
public interface CoinDeskClient {

    @RequestLine("GET /v1/bpi/historical/close.json?start={start}&end={end}&currency={currency}")
    Object getHistoricalData(
            @Param("start") String start,
            @Param("end") String end,
            @Param("currency") String currency
    );

    @RequestLine("GET /v1/bpi/convert.json?from={from}&to={to}&amount={amount}")
    Object convertCurrency(
            @Param("from") String from,
            @Param("to") String to,
            @Param("amount") double amount
    );
}
