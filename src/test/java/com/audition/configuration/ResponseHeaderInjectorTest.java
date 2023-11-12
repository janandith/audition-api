package com.audition.configuration;

import brave.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ResponseHeaderInjectorTest {

    private transient ResponseHeaderInjector responseHeaderInjector;

    @BeforeEach
    public void setup() {
        final Tracer tracer = Mockito.mock(Tracer.class);
        Mockito.when(tracer.currentSpan()).thenReturn(null);
        this.responseHeaderInjector = new ResponseHeaderInjector(tracer);
    }

    @Test
    void doFilter() throws IOException, ServletException {
        final ServletRequest servletRequest = Mockito.mock(ServletRequest.class);
        final ServletResponse servletResponse = Mockito.mock(ServletResponse.class);
        final FilterChain filterChain = Mockito.mock(FilterChain.class);
        responseHeaderInjector.doFilter(servletRequest, servletResponse, filterChain);
        Mockito.verify(filterChain, Mockito.atLeastOnce()).doFilter(servletRequest, servletResponse);
    }
}
