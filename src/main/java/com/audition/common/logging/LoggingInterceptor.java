package com.audition.common.logging;

import java.io.IOException;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
        throws IOException {
        logRequest(request, body);
        final ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("===Begin Request Log===");
            LOG.debug("URI: {}", request.getURI());
            LOG.debug("Method: {}", request.getMethod());
            LOG.debug("Headers: {}", request.getHeaders());
            LOG.debug("Request body: {}", new String(body, "UTF-8"));
            LOG.debug("===End Request LOG===");
        }
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("===log response start===");
            LOG.debug("Response status code: {}", response.getStatusCode());
            LOG.debug("Response status text: {}", response.getStatusText());
            LOG.debug("Headers: {}", response.getHeaders());
            LOG.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            LOG.debug("===log response end===");
        }
    }
}
