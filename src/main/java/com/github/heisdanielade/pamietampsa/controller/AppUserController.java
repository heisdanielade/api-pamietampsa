package com.github.heisdanielade.pamietampsa.controller;

import com.github.heisdanielade.pamietampsa.dto.user.UserResponseDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.exception.auth.AccountNotFoundException;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.response.BaseApiResponse;
import com.github.heisdanielade.pamietampsa.service.AppUserService;
import com.github.heisdanielade.pamietampsa.util.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "App User", description = "User management (CRUD) endpoints")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1", produces = "application/json")
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
    @GetMapping(path = "/user")
    public ResponseEntity<BaseApiResponse<UserResponseDto>> baseUserInfo(Principal principal){
        String userEmail = principal.getName();
        AppUser currentUser = appUserRepository.findByEmail(userEmail)
                .orElseThrow(AccountNotFoundException::new);

        UserResponseDto data = DtoMapper.toUserDto(currentUser);

        BaseApiResponse<UserResponseDto> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "Currently logged in user details.",
                data
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all users",
            description = "Returns details of all users, only accessible to App User if hasRole('ADMIN')"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    // TODO: implement role based access
    @Cacheable("users")
    @GetMapping("/users/all")
    public ResponseEntity<BaseApiResponse<List<UserResponseDto>>> allUsers(){
        List<AppUser> users = appUserService.allUsers();

        List<UserResponseDto> userDtoList = users.stream().map(DtoMapper::toUserDto).toList();
        BaseApiResponse<List<UserResponseDto>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "Users fetched successfully.",
                userDtoList);

        return ResponseEntity.ok(response);
    }

}
