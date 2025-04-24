package com.example.backend.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJdbcRepositories
public class DatabaseConfig extends AbstractJdbcConfiguration {

    @Bean
    DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/newdb")
                .username("root")
                .password("silversimonadmin")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.use_sql_comments", "true");
        return properties;
    }

}
