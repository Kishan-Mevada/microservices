package com.nk.microservices.limitsservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "limits-service")
@Getter
@Setter
public class LimitConfiguration {
    private int max;
    private int min;
}
