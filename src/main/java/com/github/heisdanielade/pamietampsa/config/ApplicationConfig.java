package com.github.heisdanielade.pamietampsa.config;

import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AppUserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("(e) User not found."));
    }
}
