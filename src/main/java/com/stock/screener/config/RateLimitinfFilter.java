package com.stock.screener.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class RateLimitinfFilter implements ExchangeFilterFunction {

    private final RateLimiter rateLimiter;

    public RateLimitinfFilter() {
        this.rateLimiter = RateLimiter.create(5);
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        rateLimiter.acquire();
        return next.exchange(request);
    }
}
