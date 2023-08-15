package com.assessment.tracker.clients.coindesk;

import com.assessment.tracker.clients.coindesk.config.CoinDeskFeignConfig;
import feign.Headers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpConnectTimeoutException;

@Headers("Accept: application/json")
@FeignClient(name = "coinDeskClient", url = "${coindesk.api.url}", configuration = CoinDeskFeignConfig.class, fallback = CoinDeskClientFallback.class)
public interface CoinDeskClient {

    /**
     * Used for fetching historical BTC Price only.
     *
     * @param start    : in YYYY-MM-DD format.
     * @param end      : in YYYY-MM-DD format.
     * @param currency : target currency for conversion.
     * @return
     */

    @Headers("Accept: application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/bpi/historical/close.json", headers = {"Accept=application/json"})
    String getHistoricalBitcoinPriceData(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("currency") String currency
    ) throws HttpConnectTimeoutException;

    /**
     * Used to convert one currency to another
     *
     * @param from   : in YYYY-MM-DD format.
     * @param to     : in YYYY-MM-DD format.
     * @param amount : amount to be converted.
     * @return
     */

    @Headers("Accept: application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/bpi/convert.json", headers = {"Accept=application/json"})
    String convertCurrency(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") double amount
    );

    @Headers("Accept: application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/bpi/supported-currencies.json", headers = {"Accept=application/json"})
    String getSupportedCurrencies();
}
