package io.concert_booking.infrastructure;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.infrastructure.account.AccountHistoryRepositoryImpl;
import io.concert_booking.infrastructure.account.jpa.AccountHistoryJpaRepository;
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
class AccountHistoryRepositoryImplTest {

    @InjectMocks
    AccountHistoryRepositoryImpl accountHistoryRepository;

    @Mock
    AccountHistoryJpaRepository accountHistoryJpaRepository;

    @Test
    @DisplayName("거래내역 가져오기 - 내역이 없을 경우")
    void getAccountHistoryTest01() {
        // given
        long accountHistoryId = 1L;
        doReturn(Optional.empty()).when(accountHistoryJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(ConcertBookingException.class, () -> accountHistoryRepository.getAccountHistoryById(accountHistoryId));

        // then
        assertEquals("해당 ID로 찾을 수 없음", throwable.getMessage());
    }

}