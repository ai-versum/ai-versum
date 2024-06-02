package aiversum.backend.rest.dto;

import java.util.List;

public record OllamaModelResponse(List<OllamaModel> models) {
    public record OllamaModel(String name) {
    }
}
