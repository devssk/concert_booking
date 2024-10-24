package io.concert_booking.infrastructure;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.infrastructure.concert.ConcertSeatRepositoryImpl;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertSeatRepositoryImplTest {

    @InjectMocks
    ConcertSeatRepositoryImpl concertSeatRepository;

    @Mock
    ConcertSeatJpaRepository concertSeatJpaRepository;

    @Test
    @DisplayName("콘서트 좌석 불러오기 - 콘서트 좌석이 없을 경우")
    void getConcertSeatTest() {
        // given
        long concertSeatId = 1L;
        doReturn(Optional.empty()).when(concertSeatJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(ConcertBookingException.class, () -> concertSeatRepository.getConcertSeatById(concertSeatId));

        // then
        assertEquals("해당 ID로 찾을 수 없음", throwable.getMessage());
    }

}