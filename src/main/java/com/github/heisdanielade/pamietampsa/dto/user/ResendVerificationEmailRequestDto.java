package com.github.heisdanielade.pamietampsa.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResendVerificationEmailRequestDto {
    @NotBlank(message = "Email is required")
    private String email;
}
