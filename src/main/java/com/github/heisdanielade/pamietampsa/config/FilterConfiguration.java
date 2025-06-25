package com.github.heisdanielade.pamietampsa.config;

import com.github.heisdanielade.pamietampsa.filter.CustomRateLimitingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FilterConfiguration {

    private final List<String> ENDPOINTS = List.of("/v1/auth/*", "/v1/user/edit");

    @Bean
    public FilterRegistrationBean<CustomRateLimitingFilter> rateLimitingFilter() {
        FilterRegistrationBean<CustomRateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomRateLimitingFilter());
        registrationBean.addUrlPatterns(ENDPOINTS.toArray(new String[0]));
        registrationBean.setOrder(1); // priority
        return registrationBean;
    }
}
