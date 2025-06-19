package com.github.heisdanielade.pamietampsa.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    @Schema(description = "User's email", example = "johndoe@test.com")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "User's password (raw input)")
    @NotBlank(message = "Password is required")
    private String password;
}
