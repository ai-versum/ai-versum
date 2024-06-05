package aiversum.backend.rest.dto;

import java.util.List;

public record GeminiModelResponse(List<GeminiModel> geminiModels) {
    public record GeminiModel(String name){
    }
}
