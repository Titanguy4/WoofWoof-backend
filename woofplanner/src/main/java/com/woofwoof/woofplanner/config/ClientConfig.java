package com.woofwoof.woofplanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpExchangeAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.woofwoof.woofplanner.client.StayClient;

@Configuration
public class ClientConfig {

    @Value("${application.config.stay-service-url}")
    private String stayServiceUrl;

    @Bean
    public StayClient stayClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(stayServiceUrl)
                .build();

        HttpExchangeAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(StayClient.class);
    }
}
