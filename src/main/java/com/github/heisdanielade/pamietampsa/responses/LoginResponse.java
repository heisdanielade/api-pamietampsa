package com.github.heisdanielade.pamietampsa.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRespnse {
    private String token;
    private long expiresIn;
}
