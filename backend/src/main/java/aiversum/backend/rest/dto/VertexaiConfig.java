package aiversum.backend.rest.dto;

public record VertexaiConfig (
        boolean enabled,
        String projectName,
        String location,
        String imageStyle,
        String imageSize,
        String apiKey
){
}
