package aiversum.backend.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChatCommand(
    String messages,
    Map<String, String> options
) {
}
