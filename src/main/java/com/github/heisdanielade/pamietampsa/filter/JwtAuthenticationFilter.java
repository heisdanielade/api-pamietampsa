package com.github.heisdanielade.pamietampsa.filter;

import com.github.heisdanielade.pamietampsa.service.auth.CustomUserDetailsService;
import com.github.heisdanielade.pamietampsa.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        System.out.println("\n==== [JwtAuthFilter] Executing filter\n"); // Debug log

//        Check if the header is missing or incorrect
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            final String jwt = authHeader.substring(7);
            final String userIdStr = jwtService.extractUserId(jwt);
            logger.debug("=== [JwtAuthFilter] Extracted User Id: " + userIdStr);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(userIdStr != null && authentication == null){
                UserDetails userDetails = this.userDetailsService.loadUserById(Long.parseLong(userIdStr));

                if(jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            System.out.println("\n==== [JwtAuthFilter] Executed Filter\n");
            filterChain.doFilter(request, response);
        } catch (Exception exception){
            System.out.println("(e) " + exception.getMessage());
            SecurityContextHolder.clearContext(); // clean up
            System.out.println("=== [JwtAuthFilter] (e) " + exception.getClass().getName());
            if (exception instanceof AuthenticationException) {
                throw exception;
            } else {
                throw new BadCredentialsException("Invalid or missing token", exception);
            }
        }
    }
}
