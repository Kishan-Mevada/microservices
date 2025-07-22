package com.nk.microservices.currencyconversionservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyExchangeProxy currencyExchangeProxy;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariables);

        CurrencyConversion conversion = responseEntity.getBody();

        return new CurrencyConversion(conversion.getId(), from, to, quantity,
                conversion.getConversionMultiple(), quantity.multiply(conversion.getConversionMultiple()),
                conversion.getEnvironment());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversionFeign(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity) {

        CurrencyConversion conversion = currencyExchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(conversion.getId(), from, to, quantity,
                conversion.getConversionMultiple(), quantity.multiply(conversion.getConversionMultiple()),
                conversion.getEnvironment());
    }
}
