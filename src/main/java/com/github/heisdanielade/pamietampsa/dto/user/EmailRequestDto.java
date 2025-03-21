package com.github.heisdanielade.pamietampsa.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailRequestDto {
    @NotBlank(message = "Email is required")
    private String email;
}
