package com.prueba.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsConfig {

    private static final Logger log = LoggerFactory.getLogger(AwsConfig.class);

    @Bean
    public SesClient sesClient(@Value("${ses.region:us-east-1}") String region) {
        try {
            // Verificar si hay credenciales disponibles antes de construir el cliente
            DefaultCredentialsProvider.create().resolveCredentials();
            return SesClient.builder()
                    .region(Region.of(region))
                    .build();
        } catch (SdkClientException e) {
            // Sin credenciales AWS (Railway, local sin config) → cliente no-op
            log.warn("No se encontraron credenciales AWS. El envío de email estará desactivado.");
            return SesClient.builder()
                    .region(Region.of(region))
                    .credentialsProvider(() -> { throw new SdkClientException("No AWS credentials available"); })
                    .build();
        }
    }
}
