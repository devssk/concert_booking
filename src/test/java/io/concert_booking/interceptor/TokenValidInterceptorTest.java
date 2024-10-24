package io.concert_booking.interceptor;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.queue.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenValidInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TokenService tokenService;

    @Test
    @DisplayName("헤더에 토큰이 없을 경우")
    public void TokenIsNull() throws Exception {
        mockMvc.perform(patch("/accounts/payment"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/concerts/info/seats"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/concerts/seats"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/queues/waiting_number"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("토큰이 유효하지 않을 경우")
    public void TokenIsNotValid() throws Exception {
        when(tokenService.decodeToken(anyString())).thenThrow(new ConcertBookingException(ErrorCode.TOKEN_INVALID));

        mockMvc.perform(patch("/accounts/payment")
                        .header("Authorization", "invalid_token"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/concerts/info/seats")
                        .header("Authorization", "invalid_token"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/concerts/seats")
                        .header("Authorization", "invalid_token"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/queues/waiting_number")
                        .header("Authorization", "invalid_token"))
                .andExpect(status().isUnauthorized());
    }

}