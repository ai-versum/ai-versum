package aiversum.backend.config.properties;

public record VertexaiProperties(
        String apiKey,
        String projectName,
        String location,
        boolean enabled
){
}
