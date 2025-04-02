package com.github.heisdanielade.pamietampsa.response.auth;

import com.github.heisdanielade.pamietampsa.enums.Role;

public record RegisterResponse(String email, Role role){}
