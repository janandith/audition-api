package com.audition.common.logging;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

class LoggingInterceptorTest {

    private transient LoggingInterceptor loggingInterceptor;

    @BeforeEach
    public void setup() {
        this.loggingInterceptor = new LoggingInterceptor();
    }

    @Test
    void intercept() throws IOException {
        final ClientHttpRequestExecution clientHttpRequestExecution = Mockito.mock(ClientHttpRequestExecution.class);
        final HttpRequest mockRequest = Mockito.mock(HttpRequest.class);
        Mockito.when(clientHttpRequestExecution.execute(mockRequest, new byte[] {})).thenReturn(Mockito.mock(ClientHttpResponse.class));
        Assertions.assertNotNull(loggingInterceptor.intercept(mockRequest, new byte[] {}, clientHttpRequestExecution));
    }
}
