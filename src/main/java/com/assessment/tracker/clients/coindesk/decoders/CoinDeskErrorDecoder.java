package com.assessment.tracker.clients.coindesk.decoders;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpException;

public class CoinDeskErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // Customize error handling based on the response status, etc.
        return new HttpException("CoinDesk API Error: " + response.status());
    }
}
