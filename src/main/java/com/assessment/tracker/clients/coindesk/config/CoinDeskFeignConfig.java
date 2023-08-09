package com.assessment.tracker.clients.coindesk.config;

import com.assessment.tracker.clients.coindesk.decoders.CoinDeskErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CoinDeskFeignConfig {
    private final int connectTimeout;
    private final int readTimeout;
    @Autowired
    public CoinDeskFeignConfig(
            @Value("${feign.client.config.default.connectTimeout}") int connectTimeout,
            @Value("${feign.client.config.default.readTimeout}") int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CoinDeskErrorDecoder();
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .build();
    }
}
