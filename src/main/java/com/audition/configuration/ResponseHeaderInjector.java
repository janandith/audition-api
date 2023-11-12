package com.audition.configuration;

import brave.Span;
import brave.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class ResponseHeaderInjector extends GenericFilterBean {

    private transient Tracer tracer;

    @Autowired
    public ResponseHeaderInjector(final Tracer tracer) {
        super();
        this.tracer = tracer;
    }

    // Inject openTelemetry trace and span Ids in the response headers.
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final Span currentSpan = this.tracer.currentSpan();
        if (currentSpan == null) {
            chain.doFilter(request, response);
            return;
        }

        ((HttpServletResponse) response).addHeader("TraceId",
                currentSpan.context().traceIdString());
        ((HttpServletResponse) response).addHeader("SpanId",
                currentSpan.context().spanIdString());
        chain.doFilter(request, response);
    }
}
