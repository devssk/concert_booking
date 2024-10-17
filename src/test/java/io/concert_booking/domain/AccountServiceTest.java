package io.concert_booking.domain;

import io.concert_booking.domain.account.dto.AccountDomainDto;
import io.concert_booking.domain.account.entity.Account;
import io.concert_booking.domain.account.repository.AccountHistoryRepository;
import io.concert_booking.domain.account.repository.AccountRepository;
import io.concert_booking.domain.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountHistoryRepository accountHistoryRepository;

    @Test
    @DisplayName("결제시 잔액 체크")
    void paymentTest() throws Exception {
        // given
        long accountId = 1L;
        long memberId = 1L;
        long amount = 2000L;
        long balance = 1000L;

        Account account = new Account(memberId, balance);
        Field createdAtField = Account.class.getDeclaredField("accountId");
        createdAtField.setAccessible(true);
        createdAtField.set(account, accountId);

        AccountDomainDto.PaymentAccountCommand command = new AccountDomainDto.PaymentAccountCommand(accountId, amount);

        doReturn(account).when(accountRepository).getAccountByIdForUpdate(anyLong());

        // when
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> accountService.paymentAccount(command));

        // then
        assertEquals("잔액이 부족합니다.", throwable.getMessage());
    }

}