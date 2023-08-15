package com.assessment.tracker.clients.coindesk.decoders;

import feign.Response;
import org.apache.http.HttpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CoinDeskErrorDecoderTest {


    @Mock
    private Response response;

    private CoinDeskErrorDecoder coinDeskErrorDecoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        coinDeskErrorDecoder = new CoinDeskErrorDecoder();
    }

    @Test
    public void testDecode() {
        when(response.status()).thenReturn(404); // Mock the response status

        Exception exception = coinDeskErrorDecoder.decode("methodKey", response);

        assertNotNull(exception);
        assertTrue(exception instanceof HttpException);
        assertEquals("CoinDesk API Error: 404", exception.getMessage());
    }


}