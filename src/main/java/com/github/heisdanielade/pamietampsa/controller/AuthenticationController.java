package com.github.heisdanielade.pamietampsa.controller;

import com.github.heisdanielade.pamietampsa.dto.user.LoginUserDto;
import com.github.heisdanielade.pamietampsa.dto.user.RegisterUserDto;
import com.github.heisdanielade.pamietampsa.dto.user.ResendVerificationEmailRequestDto;
import com.github.heisdanielade.pamietampsa.dto.user.VerifyUserDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.response.BaseApiResponse;
import com.github.heisdanielade.pamietampsa.service.auth.AuthenticationService;
import com.github.heisdanielade.pamietampsa.service.auth.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth", description = "Registration & Authentication endpoints")
@RestController
@RequestMapping(path = "/v1/auth", produces = "application/json")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and sends a confirmation email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully. Proceed to email verification"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict, Account already exists")
    })
    @PostMapping(path ="/signup")
    public ResponseEntity<BaseApiResponse<Map<String, Object>>> register(@Valid @RequestBody RegisterUserDto input) {
        AppUser registeredUser = authenticationService.signup(input);

        Map<String, Object> data = new HashMap<>();
        data.put("email", registeredUser.getEmail());
        data.put("role", registeredUser.getRole());
        data.put("enabled", registeredUser.isEnabled());

        BaseApiResponse<Map<String, Object>> response = new BaseApiResponse<>(
                HttpStatus.CREATED.value(),
                "User registered successfully. Proceed to email verification.",
                data
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Login existing user",
            description = "Authenticates a user using email and password, returns a JWT token with expiry timeline upon successful login"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Bad Request, Invalid Email or Password"),
            @ApiResponse(responseCode = "403", description = "Forbidden, Email not verified"),
    })
    @PostMapping(path = "/login")
    public ResponseEntity<BaseApiResponse<Map<String, Object>>> authenticate(@RequestBody LoginUserDto input) {
        AppUser authenticatedUser = authenticationService.authenticate(input);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        Map<String, Object> data = new HashMap<>();
        data.put("token", jwtToken);
        data.put("expirationTime", jwtService.getEXPIRATION_TIME());

        BaseApiResponse<Map<String, Object>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "User authenticated successfully.",
                data
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            summary = "Verify user's email",
            description = "Verify a user's email address using OTP sent via email. If successful, isEnabled is set to true"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    @PostMapping(path = "/verify-email")
    public ResponseEntity<BaseApiResponse<Map<String, Object>>> verifyUser(@RequestBody VerifyUserDto input){
        authenticationService.verifyUser(input);
        BaseApiResponse<Map<String, Object>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "Email verified successfully."
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            summary = "Resend verification email",
            description = "Resends the verification email to the user if their email is not yet verified"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verification code sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "User with given email not found"),
            @ApiResponse(responseCode = "409", description = "Email already verified")
    })
    @PostMapping(path = "/resend-verification-email")
    public ResponseEntity<BaseApiResponse<Map<String, Object>>> resendVerificationCode(@RequestBody ResendVerificationEmailRequestDto input){
        authenticationService.resendVerificationEmail(input);
        BaseApiResponse<Map<String, Object>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "Email verification code sent successfully."
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
