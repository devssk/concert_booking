package io.concert_booking.infrastructure;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.infrastructure.concert.BookingRepositoryImpl;
import io.concert_booking.infrastructure.concert.jpa.BookingJpaRepository;
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
class BookingRepositoryImplTest {

    @InjectMocks
    BookingRepositoryImpl bookingRepository;

    @Mock
    BookingJpaRepository bookingJpaRepository;

    @Test
    @DisplayName("예약내역 가져오기 - 예약내역이 없을 경우")
    void getBookingTest() {
        // given
        long bookingId = 1L;
        doReturn(Optional.empty()).when(bookingJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(ConcertBookingException.class, () -> bookingRepository.getBookingById(bookingId));

        // then
        assertEquals("해당 ID로 찾을 수 없음", throwable.getMessage());
    }

}