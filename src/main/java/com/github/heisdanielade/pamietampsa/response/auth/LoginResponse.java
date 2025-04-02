package com.github.heisdanielade.pamietampsa.response.auth;


public record LoginResponse(String token, long expiresIn) {}
