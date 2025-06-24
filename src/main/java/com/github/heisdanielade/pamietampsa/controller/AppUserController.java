package com.github.heisdanielade.pamietampsa.controller;

import com.github.heisdanielade.pamietampsa.dto.user.UserPatchDto;
import com.github.heisdanielade.pamietampsa.dto.user.UserResponseDto;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.response.BaseApiResponse;
import com.github.heisdanielade.pamietampsa.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Tag(name = "App User", description = "User management (CRUD) endpoints")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/user", produces = "application/json")
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    @Operation(
            summary = "Get user details",
            description = "Returns details of the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @Cacheable("users")
    @GetMapping(path = "")
    public ResponseEntity<BaseApiResponse<UserResponseDto>> baseUserInfo(Principal principal){

        UserResponseDto data = appUserService.getUserInfo(principal);

        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(
          HttpStatus.OK.value(),
          "User info fetched successfully.",
          data
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Edit user details",
            description = "Edit's currently logged in user's details. PATCH"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "0K"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping(path = "/edit")
    public ResponseEntity<BaseApiResponse<Map<String, Object>>> modifyUserInfo(@Valid @RequestBody UserPatchDto userPatchDto, Principal principal){
        appUserService.editUserInfo(userPatchDto, principal);

        BaseApiResponse<Map<String, Object>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "User info modified successfully."
        );
        return ResponseEntity.ok(response);
    }

}
