package io.concert_booking.infrastructure;

import io.concert_booking.infrastructure.concert.ConcertRepositoryImpl;
import io.concert_booking.infrastructure.concert.jpa.ConcertJpaRepository;
import io.concert_booking.infrastructure.exception.EntityRowNotFoundException;
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
class ConcertRepositoryImplTest {

    @InjectMocks
    ConcertRepositoryImpl concertRepository;

    @Mock
    ConcertJpaRepository concertJpaRepository;

    @Test
    @DisplayName("콘서트 가져오기 - 콘서트가 없을 경우")
    void getConcertTest() {
        // given
        long concertId = 1L;
        doReturn(Optional.empty()).when(concertJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(EntityRowNotFoundException.class, () -> concertRepository.getConcertById(concertId));

        // then
        assertEquals("해당 콘서트를 찾을 수 없습니다.", throwable.getMessage());
    }

}