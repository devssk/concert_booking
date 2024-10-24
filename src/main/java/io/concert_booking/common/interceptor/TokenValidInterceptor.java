package io.concert_booking.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.common.exception.ResponseBody;
import io.concert_booking.domain.queue.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            Object startTime = request.getAttribute("StartTime");
            if (startTime instanceof Long) {
                long endTime = System.currentTimeMillis();
                log.warn("Request URL : {}, StartTime : {}, error : {}, Request processing time : {} ms", request.getRequestURI(), startTime, ErrorCode.VALID_ERROR.name(), endTime - (Long) startTime);
            }
            return createErrorResponse(response);
        }
        try {
            tokenService.decodeToken(token);
        } catch (ConcertBookingException e) {
            Object startTime = request.getAttribute("StartTime");
            if (startTime instanceof Long) {
                long endTime = System.currentTimeMillis();
                log.warn("Request URL : {}, StartTime : {}, error : {}, Request processing time : {} ms", request.getRequestURI(), startTime, e.getErrorCode().name(), endTime - (Long) startTime);
            }
            return createErrorResponse(response, e);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean createErrorResponse(HttpServletResponse response, ConcertBookingException e) throws IOException {
        ResponseBody responseBody = new ResponseBody(e.getErrorCode().getMessage(), e.getErrorCode().name());
        response.setStatus(e.getErrorCode().getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));

        return false;
    }

    private boolean createErrorResponse(HttpServletResponse response) throws IOException {
        ResponseBody responseBody = new ResponseBody(ErrorCode.VALID_ERROR.getMessage() + ", " + "token", ErrorCode.VALID_ERROR.name());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));

        return false;
    }

}
