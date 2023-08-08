package com.assessment.tracker.clients.coindesk.decoders;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CoinDeskErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // Customize error handling based on the response status, etc.
        return new Exception("CoinDesk API Error: " + response.status());
    }
}
