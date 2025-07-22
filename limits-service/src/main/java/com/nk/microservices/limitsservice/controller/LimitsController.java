package com.nk.microservices.limitsservice.controller;

import com.nk.microservices.limitsservice.bean.Limits;
import com.nk.microservices.limitsservice.config.LimitConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitsController {

    private final LimitConfiguration limitConfiguration;

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(limitConfiguration.getMax(), limitConfiguration.getMin());
    }
}
