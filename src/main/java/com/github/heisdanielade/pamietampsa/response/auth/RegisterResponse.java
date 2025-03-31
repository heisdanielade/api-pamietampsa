package com.github.heisdanielade.pamietampsa.response.auth;

import com.github.heisdanielade.pamietampsa.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponse {
    private String email;
    private Role role;
}
