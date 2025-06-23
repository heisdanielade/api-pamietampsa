package com.github.heisdanielade.pamietampsa.config;

import com.github.heisdanielade.pamietampsa.filter.RateLimitingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter() {
        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitingFilter());
        registrationBean.addUrlPatterns("/v1/auth/*"); // Applies only to auth endpoints
        registrationBean.setOrder(1); // priority
        return registrationBean;
    }
}
