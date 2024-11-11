package aiversum.backend.rest.dto;

public record OpenaiConfig (
        String imageSize,
        String imageStyle,
        boolean enabled,
        String apiKey
) {
}
