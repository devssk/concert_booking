package io.concert_booking.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Slf4j
@Component
public class ApiLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String apiUUID = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        log.info("ApiUUID : {}, Request URL : {}, Method : {}, StartTime : {}", apiUUID, requestURI, method, nowTime);

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            String endLocalTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("ApiUUID : {}, Request URL : {}, Method : {}, endTime : {}, Request processing time : {} ms", apiUUID, requestURI, method, endLocalTime, endTime - startTime);
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
