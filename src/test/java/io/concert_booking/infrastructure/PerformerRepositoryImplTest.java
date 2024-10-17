package io.concert_booking.infrastructure;

import io.concert_booking.infrastructure.concert.PerformerRepositoryImpl;
import io.concert_booking.infrastructure.concert.jpa.PerformerJpaRepository;
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
class PerformerRepositoryImplTest {

    @InjectMocks
    PerformerRepositoryImpl performerRepository;

    @Mock
    PerformerJpaRepository performerJpaRepository;

    @Test
    @DisplayName("공연자 가져오기 - 공연자가 없는 경우")
    void getPerformerTest() {
        // given
        long performerId = 1L;
        doReturn(Optional.empty()).when(performerJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(EntityRowNotFoundException.class, () -> performerRepository.getPerformerById(performerId));

        // then
        assertEquals("해당 공연자를 찾을 수 없습니다.", throwable.getMessage());
    }

}