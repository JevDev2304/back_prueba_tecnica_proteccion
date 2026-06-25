package com.prueba.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    @ConditionalOnProperty(name = "DATABASE_URL", matchIfMissing = false)
    public DataSource railwayDataSource() {
        String rawUrl = System.getenv("DATABASE_URL");
        // Railway da: postgresql://user:pass@host:port/db
        // Spring necesita: jdbc:postgresql://host:port/db
        String jdbcUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");

        // Extraer user y pass de la URL
        String withoutProto = rawUrl.replace("postgresql://", "");
        String userPass = withoutProto.split("@")[0];
        String user = userPass.split(":")[0];
        String pass = userPass.split(":")[1];
        String hostDb = "jdbc:postgresql://" + withoutProto.split("@")[1];

        log.info("Conectando a Railway PostgreSQL");

        return DataSourceBuilder.create()
                .url(hostDb)
                .username(user)
                .password(pass)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
