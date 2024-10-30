package com.goruna.spring.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    @PostConstruct
    public void setUpTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
