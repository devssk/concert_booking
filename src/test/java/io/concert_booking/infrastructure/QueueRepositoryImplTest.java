package io.concert_booking.infrastructure;

import io.concert_booking.infrastructure.exception.EntityRowNotFoundException;
import io.concert_booking.infrastructure.queue.QueueRepositoryImpl;
import io.concert_booking.infrastructure.queue.jpa.QueueJpaRepository;
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
class QueueRepositoryImplTest {

    @InjectMocks
    QueueRepositoryImpl queueRepository;

    @Mock
    QueueJpaRepository queueJpaRepository;

    @Test
    @DisplayName("대기열 가져오기 - 대기열이 없을 경우")
    void getQueueTest() {
        // given
        long queueId = 1;
        doReturn(Optional.empty()).when(queueJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(EntityRowNotFoundException.class, () -> queueRepository.getQueueById(queueId));

        // then
        assertEquals("해당 대기열이 없습니다.", throwable.getMessage());
    }

}