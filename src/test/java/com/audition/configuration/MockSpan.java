package com.audition.configuration;

import brave.SpanCustomizer;

public class MockSpan implements SpanCustomizer {

    @Override
    public SpanCustomizer name(final String name) {
        return null;
    }

    @Override
    public SpanCustomizer tag(final String key, final String value) {
        return null;
    }

    @Override
    public SpanCustomizer annotate(final String value) {
        return null;
    }
}
