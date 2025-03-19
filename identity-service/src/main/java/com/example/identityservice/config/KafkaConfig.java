package com.example.identityservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConfig {
    @Bean
    JsonMessageConverter converter(){
        return new JsonMessageConverter();
    }

}
