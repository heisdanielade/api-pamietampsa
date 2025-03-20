package com.github.heisdanielade.pamietampsa.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {

    private String email;
    private String verificationCode;
}
