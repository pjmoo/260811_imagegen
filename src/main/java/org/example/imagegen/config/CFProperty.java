package org.example.imagegen.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.cf")
public record CFProperty(String accountId, String apiToken) {
}
