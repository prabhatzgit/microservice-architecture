package com.pkg.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    @LoadBalanced
    /*How to enable restTemplate so that it shouldn't use service's port, but
    * it uses service's name?
    * when more than one microservice instances needs to do distribute the load
    * and in this approach, it starts to use service name */
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
