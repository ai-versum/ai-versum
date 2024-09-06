package aiversum.backend.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ImageQueryResponse (List<ResponseList> data){
    public record ResponseList (@JsonProperty("url") String url){
    }
}
