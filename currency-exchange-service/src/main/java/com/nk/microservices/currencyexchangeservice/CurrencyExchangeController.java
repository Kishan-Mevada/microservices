package com.nk.microservices.currencyexchangeservice;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final Environment environment;
    private final CurrencyExchangeRepository currencyExchangeRepository;

    @GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = Optional.ofNullable(currencyExchangeRepository.findByFromAndTo(from, to))
                .orElseThrow(() -> new RuntimeException("Unable to find currency exchange"));

        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
