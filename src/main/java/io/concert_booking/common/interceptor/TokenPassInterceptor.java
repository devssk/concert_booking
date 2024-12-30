package io.concert_booking.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.common.exception.ResponseBody;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenPassInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final QueueService queueService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        Map<String, Long> payload = tokenService.decodeToken(token);

        long concertId = payload.get("concertId");
        long memberId = payload.get("memberId");

        boolean isWait = queueService.existsWaitQueue(new QueueDomainDto.ExistsWaitQueueCommand(concertId, memberId));
        boolean isPass = queueService.existsPassQueue(new QueueDomainDto.ExistsPassQueueCommand(concertId, memberId));

        if (isWait || !isPass) {
            log.warn("Request URL : {}, error : {}", request.getRequestURI(), ErrorCode.NOT_ENTER_TOKEN.name());
            return createErrorResponse(response, ErrorCode.NOT_ENTER_TOKEN);
        }

        QueueDomainDto.GetPassQueueStatusInfo passQueueStatus = queueService.getPassQueueStatus(new QueueDomainDto.GetPassQueueStatusCommand(concertId, memberId));

        if (requestURI.startsWith("/accounts/payment")) {
            if (!passQueueStatus.queueStatus().equals(QueueStatus.OCCUPANCY.name())) {
                log.warn("Request URL : {}, error : {}", request.getRequestURI(), ErrorCode.NOT_OCCUPANCY_TOKEN.name());
                return createErrorResponse(response, ErrorCode.NOT_OCCUPANCY_TOKEN);
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean createErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ResponseBody responseBody = new ResponseBody(errorCode.getMessage(), errorCode.name());
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));

        return false;
    }
}
