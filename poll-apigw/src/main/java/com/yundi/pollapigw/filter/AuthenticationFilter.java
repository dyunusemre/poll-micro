package com.yundi.pollapigw.filter;

import com.yundi.pollapigw.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    private final List<String> openEndpoints = List.of(
            "/register",
            "/login",
            "/question/all"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Predicate<ServerHttpRequest> isApiSecure = serverHttpRequest ->
                openEndpoints.stream()
                        .noneMatch(s -> request.getURI().getPath().contains(s));

        if (isApiSecure.test(request)) {
            if (isInvalidToken(request))
                return onUnauthorized(exchange);

            setAuthorizationHeaders(exchange);
        }
        return chain.filter(exchange);
    }

    private boolean isInvalidToken(ServerHttpRequest request) {
        return isAuthorizationMissing(request) || jwtUtil.isInvalidToken(getAuthorizationHeader(request));
    }

    private Mono<Void> onUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private void setAuthorizationHeaders(ServerWebExchange exchange) {
        String token = getAuthorizationHeader(exchange.getRequest());
        exchange.getRequest().mutate()
                .header("username", jwtUtil.extractUsername(token))
                .header("role", jwtUtil.extractRole(token))
                .build();
    }

    private String getAuthorizationHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring("Bearer ".length());
    }

    private boolean isAuthorizationMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
