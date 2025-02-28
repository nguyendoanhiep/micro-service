package com.example.gateway.client;

import com.example.gateway.dto.ApiResponse;
import com.example.gateway.dto.request.IntrospectRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface AuthServiceClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<?>> introspect(@RequestBody IntrospectRequest request);
}
