package aiversum.backend.rest.dto;

import java.util.List;

public record OpenAiModelResponse(List<OpenAIModel> data) {
    public record OpenAIModel(String id) {
    }
}
