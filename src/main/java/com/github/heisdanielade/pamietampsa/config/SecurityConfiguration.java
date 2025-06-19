package com.github.heisdanielade.pamietampsa.config;

import com.github.heisdanielade.pamietampsa.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final List<String> allowedRESTMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");
    private final List<String> allowedRequestHeaders = List.of("Authorization", "Content-Type", "Origin", "Accept", "X-Requested-With");


    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/v1/auth/**",
                                "/v1/hello"
                        ).permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // STATELESS: every request is a new one regardless of whether it's from the same user
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        System.out.println("\n============ Loading Cors Configuration\n");

        String frontEndDevelopmentURL = "http://localhost:3000";
        String frontEndProductionURL = "https://pamietampsa.netlify.app";

        String backendDevelopmentURL = "http://localhost:8080";
        String backendProductionURL = "https://api-pamietampsa.up.railway.app";

        configuration.setAllowedOrigins(List.of(
                frontEndDevelopmentURL,
                frontEndProductionURL,
                backendDevelopmentURL,
                backendProductionURL
        ));
        configuration.setAllowedMethods(this.allowedRESTMethods);
        configuration.setAllowedHeaders(this.allowedRequestHeaders);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
