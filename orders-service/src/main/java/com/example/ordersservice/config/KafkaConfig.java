//package com.example.ordersservice.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.support.converter.JsonMessageConverter;
//
//@Configuration
//public class KafkaConfig {
//    @Bean
//    NewTopic addCustomer(){
//        return new NewTopic("customer",2,(short) 1);
//    }
//
//    @Bean
//    JsonMessageConverter converter(){
//        return new JsonMessageConverter();
//    }
//}
