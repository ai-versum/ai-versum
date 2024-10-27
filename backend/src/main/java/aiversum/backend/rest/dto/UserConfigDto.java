package aiversum.backend.rest.dto;

public record UserConfigDto(
        OllamaConfig ollamaConfig,
        OpenaiConfig openaiConfig,
        VertexaiConfig vertexaiConfig
        ) {
}
