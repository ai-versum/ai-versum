package aiversum.backend.rest.dto;

public record CompletionCommand(
    String prompt,
    String model,
    boolean stream
) {
}
