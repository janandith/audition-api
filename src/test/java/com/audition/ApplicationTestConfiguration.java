package com.audition;

import brave.Tracer;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ApplicationTestConfiguration {
    @Bean
    @Primary
    public Tracer tracer() {
        return Mockito.mock(Tracer.class);
    }
}
