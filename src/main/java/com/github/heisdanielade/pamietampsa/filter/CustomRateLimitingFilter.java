package com.github.heisdanielade.pamietampsa.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.heisdanielade.pamietampsa.response.BaseApiResponse;
import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomRateLimitingFilter implements Filter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();


    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(
                        Bandwidth.classic(5,
                                Refill.greedy(5, Duration.ofMinutes(1)))) // max 5 requests per minute
                .build();
    }

    private Bucket resolveBucket(String ip) {
        return cache.computeIfAbsent(ip, k -> createNewBucket());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("\n==== [RateLimitingFilter] Executing filter\n"); // Debug log

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String ip = httpReq.getRemoteAddr();
        String path = httpReq.getRequestURI();
        Bucket bucket = resolveBucket(ip);

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            System.out.println("\n==== [RateLimitingFilter] (Limit exceeded):\n" +
                               "\t== IP: " + ip +
                               "\n\t== PATH: " + path + "\n");

            httpResp.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResp.setContentType("application/json");

            BaseApiResponse<Object> errorResponse = new BaseApiResponse<>(
                    HttpStatus.TOO_MANY_REQUESTS.value(),
                    "Too many requests. Try again later."
            );


            String json = objectMapper.writeValueAsString(errorResponse);
            httpResp.getWriter().write(json);
        }
    }
}

