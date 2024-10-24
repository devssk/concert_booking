package io.concert_booking.interceptor;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenPassInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TokenService tokenService;
    @MockBean
    QueueService queueService;

    @Test
    @DisplayName("토큰 상태가 유효하지 않을 경우 - 좌석조회, 좌석예약")
    public void TokenStatusTest01() throws Exception {
        long queueId = 1L;
        long concertId = 1L;
        Map<String, Long> payload = new HashMap<>();
        payload.put("queueId", 1L);

        QueueDomainDto.GetQueueInfo getQueueInfo = new QueueDomainDto.GetQueueInfo(queueId, concertId, QueueStatus.WAIT.name(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        doReturn(payload).when(tokenService).decodeToken(anyString());
        doReturn(getQueueInfo).when(queueService).getQueue(anyLong());

        mockMvc.perform(get("/concerts/info/seats")
                        .header("Authorization", "token"))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/concerts/seats")
                        .header("Authorization", "token"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("토큰 상태가 유효하지 않을 경우 - 결제")
    public void TokenStatusTest02() throws Exception {
        long queueId = 1L;
        long concertId = 1L;
        Map<String, Long> payload = new HashMap<>();
        payload.put("queueId", 1L);

        QueueDomainDto.GetQueueInfo getQueueInfo = new QueueDomainDto.GetQueueInfo(queueId, concertId, QueueStatus.WAIT.name(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        doReturn(payload).when(tokenService).decodeToken(anyString());
        doReturn(getQueueInfo).when(queueService).getQueue(anyLong());

        mockMvc.perform(patch("/accounts/payment")
                        .header("Authorization", "token"))
                .andExpect(status().isForbidden());
    }

}