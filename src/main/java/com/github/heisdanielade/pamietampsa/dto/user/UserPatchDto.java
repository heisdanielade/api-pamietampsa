package com.github.heisdanielade.pamietampsa.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchDto {

    @Schema(description = "User's email", example = "adam@test.com")
    @Email(message = "Email is invalid")
    private String email;

    @Schema(description = "User's name", example = "Adam")
    private String name;
}
