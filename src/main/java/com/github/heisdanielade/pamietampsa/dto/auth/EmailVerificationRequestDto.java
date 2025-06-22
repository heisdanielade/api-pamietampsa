package com.github.heisdanielade.pamietampsa.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationRequestDto {

    @Schema(description = "User's email", example = "johndoe@test.com")
    private String email;

    @Schema(description = "OTP code sent to user's email for verification", example = "654123")
    private String otp;
}
