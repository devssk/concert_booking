package io.concert_booking.common.config;

import io.concert_booking.common.interceptor.ApiLogInterceptor;
import io.concert_booking.common.interceptor.TokenPassInterceptor;
import io.concert_booking.common.interceptor.TokenValidInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiLogInterceptor apiLogInterceptor;
    private final TokenValidInterceptor tokenValidInterceptor;
    private final TokenPassInterceptor tokenPassInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLogInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(tokenValidInterceptor)
                .addPathPatterns("/accounts/payment", "/concerts/info/seats", "/concerts/seats", "/queues/waiting_number");

        registry.addInterceptor(tokenPassInterceptor)
                .addPathPatterns("/accounts/payment", "/concerts/info/seats", "/concerts/seats");

    }
}
