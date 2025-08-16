package com.hackhero.authmodule.config.clients;


import com.hackhero.authmodule.clients.CoreModuleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CoreModuleClientConfig {

    @Value("${clients.core-module}")
    private String coreModuleUrl;

    @Bean
    public CoreModuleClient coreModuleClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(coreModuleUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(CoreModuleClient.class);
    }
}