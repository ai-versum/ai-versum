package aiversum.backend.rest.dto;

public record LoginResponse(
        String token,
        long expiresIn
) {
}
