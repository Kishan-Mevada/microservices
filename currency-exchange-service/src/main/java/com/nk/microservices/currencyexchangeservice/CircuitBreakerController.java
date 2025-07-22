package com.nk.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class CircuitBreakerController {

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "sampleApiFallback")
 //   @CircuitBreaker(name = "sample-api", fallbackMethod = "sampleApiFallback")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default", type = Bulkhead.Type.SEMAPHORE)
    public String sampleApi() {
        log.info("Calling sampleApi");
        //new RestTemplate().getForObject("http://localhost:81000/sample-api", String.class);
        return "Hello from sampleApi";
    }

    public String sampleApiFallback(Exception e) {
        return "fallback-response";
    }

}
