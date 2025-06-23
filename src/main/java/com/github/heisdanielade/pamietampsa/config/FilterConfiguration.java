package com.github.heisdanielade.pamietampsa.config;

import com.github.heisdanielade.pamietampsa.filter.CustomRateLimitingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<CustomRateLimitingFilter> rateLimitingFilter() {
        FilterRegistrationBean<CustomRateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomRateLimitingFilter());
        registrationBean.addUrlPatterns("/v1/auth/*"); // Applies only to auth endpoints
        registrationBean.setOrder(1); // priority
        return registrationBean;
    }
}
