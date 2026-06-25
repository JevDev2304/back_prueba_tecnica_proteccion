package com.prueba.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsConfig {

    @Bean
    public SesClient sesClient(@Value("${ses.region:us-east-1}") String region) {
        return SesClient.builder()
                .region(Region.of(region))
                .build();
    }
}
