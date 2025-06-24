package com.github.heisdanielade.pamietampsa.controller;


import com.github.heisdanielade.pamietampsa.dto.user.UserResponseDto;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.response.BaseApiResponse;
import com.github.heisdanielade.pamietampsa.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "App User (ADMIN)", description = "Admin user management (CRUD) endpoints")
@RestController
@RequestMapping(path = "/v1/admin", produces = "application/json")
public class AdminController {
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public AdminController(AppUserService appUserService, AppUserRepository appUserRepository){
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }


    // Get all AppUsers in the DB
    @Operation(
            summary = "Get all users",
            description = "Returns details of all users, only accessible to App User if hasRole('ADMIN')"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @Cacheable("users")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/users")
    public ResponseEntity<BaseApiResponse<List<UserResponseDto>>> allUsers(){
        List<UserResponseDto> data = appUserService.getAllUsers();

        BaseApiResponse<List<UserResponseDto>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "All users fetched successfully",
                data
        );

        return ResponseEntity.ok(response);
    }


}
