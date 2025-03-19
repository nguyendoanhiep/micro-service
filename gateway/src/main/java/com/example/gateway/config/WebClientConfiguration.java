package com.example.gateway.config;

import com.example.gateway.client.AuthServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfiguration {
    @Value("${app.identity}")
    String domain;
    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl(domain)
                .build();
    }

    @Bean
    AuthServiceClient identityClient(WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(AuthServiceClient.class);
    }
}
