package aiversum.backend.dto;

public record RegisterUserDto(
        String email,
        String password,
        String fullName
) {
}
