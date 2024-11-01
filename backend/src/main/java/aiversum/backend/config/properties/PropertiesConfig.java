package aiversum.backend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "ai.versum")
public record PropertiesConfig(
        DefaultOptions defaultOptions
) {
}

