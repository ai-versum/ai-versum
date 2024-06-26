package aiversum.backend.rest.dto;

import java.util.List;

public record VertexaiModelResponse(List<VertexaiModel> models) {
    public record VertexaiModel(String name){
    }
}
