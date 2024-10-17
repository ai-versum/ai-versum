package aiversum.backend.rest.dto;

public record UserConfigDto(
        String imageSize,
        String imageStyle,
        String apiKey,
        String enabled,
        String projectName,
        String location
) {
}
