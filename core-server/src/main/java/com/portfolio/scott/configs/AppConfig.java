package com.portfolio.scott.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfig {

    private Tax tax;
    private Scrap scrap;

    @Getter
    @Setter
    public static class Tax {
        private int year;
    }

    @Getter
    @Setter
    public static class Scrap {
        private String url;
        private String endpoint;
        private String apiKey;
        private String createdBy;
    }
}
