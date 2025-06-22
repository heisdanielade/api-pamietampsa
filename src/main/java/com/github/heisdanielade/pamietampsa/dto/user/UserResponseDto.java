package com.github.heisdanielade.pamietampsa.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDto(
        @Schema(description = "User's email", example = "korede@test.com")
        String email,

        @Schema(description = "User's name", example = "Korede")
        String name,

        @Schema(description = "User's initial", example = "K")
        Character initial,

        @Schema(description = "User has verified email", example = "true")
        String enabled,

        @Schema(description = "User's role", example = "USER")
        String role)
{}
