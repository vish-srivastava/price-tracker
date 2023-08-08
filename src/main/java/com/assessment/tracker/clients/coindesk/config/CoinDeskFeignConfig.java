package com.assessment.tracker.clients.coindesk.config;

import com.assessment.tracker.clients.coindesk.decoders.CoinDeskErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinDeskFeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CoinDeskErrorDecoder();
    }
}
