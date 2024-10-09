package aiversum.backend.rest.dto;

public record UserConfigDto(
        String imageSize,
        String imageStyle,
        String apiKey
) {
}
