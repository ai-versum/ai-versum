package aiversum.backend.rest.dto;

public record UserConfigDto(
        String ollamaBaseUrl,
        boolean ollamaEnabled,

        String openaiApiKey,
        boolean openaiEnabled,

        String vertexaiApiKey,
        boolean vertexaiEnabled,
        String vertexaiProjectName,
        String vertexaiLocation,

        String imageSize,
        String imageStyle
        ) {
}
