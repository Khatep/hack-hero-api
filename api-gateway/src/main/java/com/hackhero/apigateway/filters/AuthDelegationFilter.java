package com.hackhero.apigateway.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthDelegationFilter implements GlobalFilter, Ordered {

    private final WebClient authWebClient;
    private final Set<String> whitelist = Set.of("/auth", "/public", "/actuator/health", "/results");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("Incoming request: {} {}", exchange.getRequest().getMethod(), path);

        if (whitelist.stream().anyMatch(path::startsWith)) {
            log.info("Request {} is whitelisted, passing through", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Request {} missing or invalid Authorization header", path);
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        log.info("Validating token for request {}", path);
        //TODO: Можно добавить кэш до вызова auth-module
        return authWebClient.get()
                .uri("/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return chain.filter(exchange);
                    } else {
                        return unauthorized(exchange, "Invalid token");
                    }
                })
                .doOnError(ex -> log.error("Auth service unavailable for request {}: {}", path, ex.getMessage()))
                .onErrorResume(ex -> unauthorized(exchange, "Auth service unavailable"));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String msg) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
