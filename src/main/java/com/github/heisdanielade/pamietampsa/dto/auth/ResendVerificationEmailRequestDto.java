package com.github.heisdanielade.pamietampsa.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResendVerificationEmailRequestDto {
    @Schema(description = "User's email", example = "johndoe@test.com")
    @NotBlank(message = "Email is required")
    private String email;
}
