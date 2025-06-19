package com.github.heisdanielade.pamietampsa.controller;

import com.github.heisdanielade.pamietampsa.dto.user.UserDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.exception.auth.AccountNotFoundException;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.response.ApiResponse;
import com.github.heisdanielade.pamietampsa.service.AppUserService;
import com.github.heisdanielade.pamietampsa.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1", produces = "application/json")
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    @GetMapping(path = "/user")
    public ResponseEntity<ApiResponse<UserDto>> baseUserInfo(Principal principal){
        String userEmail = principal.getName();
        AppUser currentUser = appUserRepository.findByEmail(userEmail)
                .orElseThrow(AccountNotFoundException::new);

        UserDto data = DtoMapper.toUserDto(currentUser);

        ApiResponse<UserDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Currently logged in user details.",
                data
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // TODO: implement role based access
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDto>> allUsers(){
        List<AppUser> users = appUserService.allUsers();

        return ResponseEntity.ok(users.stream().map(DtoMapper::toUserDto).toList());
    }

}
