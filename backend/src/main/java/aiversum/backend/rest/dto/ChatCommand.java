package aiversum.backend.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChatCommand(
    List<Message> messages,
    String model,
    boolean stream
) {
}
