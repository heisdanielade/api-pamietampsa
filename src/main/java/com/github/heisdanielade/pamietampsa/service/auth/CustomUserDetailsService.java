package com.github.heisdanielade.pamietampsa.service.auth;

import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found, Email: " + email));
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException{
        return appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found, Id: " + id));
    }
}
