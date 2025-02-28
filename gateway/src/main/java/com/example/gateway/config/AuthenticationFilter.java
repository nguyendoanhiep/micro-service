package com.example.gateway.config;

import com.example.gateway.client.AuthServiceClient;
import com.example.gateway.dto.ApiResponse;
import com.example.gateway.dto.request.IntrospectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    AuthServiceClient authServiceClient;
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter....");

        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (!StringUtil.isNullOrEmpty(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return authServiceClient.introspect(IntrospectRequest.builder()
                .path(exchange.getRequest().getPath().toString().replaceFirst("/api/v1", ""))
                .token(token)
                .build())
                .flatMap(introspectResponse -> {
            if (introspectResponse.getCode() == 200)
                return chain.filter(exchange);
            else
                return unauthenticated(exchange.getResponse(), introspectResponse);
        }).onErrorResume(throwable ->
                unauthenticated(exchange.getResponse(), ApiResponse.builder()
                        .code(exchange.getResponse().getStatusCode().value())
                        .message(throwable.getMessage())
                        .build()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response, ApiResponse<?> apiResponse) {
        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
