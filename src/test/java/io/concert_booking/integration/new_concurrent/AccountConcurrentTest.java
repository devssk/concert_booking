package io.concert_booking.integration.new_concurrent;

import io.concert_booking.domain.account.dto.AccountDomainDto;
import io.concert_booking.domain.account.entity.Account;
import io.concert_booking.domain.account.service.AccountService;
import io.concert_booking.domain.member.entity.Member;
import io.concert_booking.infrastructure.account.jpa.AccountJpaRepository;
import io.concert_booking.infrastructure.member.jpa.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountConcurrentTest {

    @Autowired
    AccountService accountService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    AccountJpaRepository accountJpaRepository;

    Logger log = LoggerFactory.getLogger(AccountConcurrentTest.class);

    @BeforeEach
    void setUp() {
        Member saveMember = memberJpaRepository.save(new Member("유애나"));
        accountJpaRepository.save(new Account(saveMember.getMemberId(), 200000L));
    }

    @Test
    @DisplayName("10건 동시 결제 일때 정상적으로 결제가 되는지 체크")
    void paymentTest01() throws Exception {
        // given
        long memberId = 1L;
        long accountId = 1L;
        long amount = 1000L;

        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            final int index = i + 1;
            executorService.execute(() -> {
                long thisAmount = amount * index;
                try {
                    accountService.paymentAccount(new AccountDomainDto.PaymentAccountCommand(accountId, thisAmount));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();

        // then
        long balance = 200000L;
        for (int i = 1; i < 11; i++) {
            balance -= (1000L * i);
        }

        Account result = accountJpaRepository.getAccountById(accountId);

        log.info("time : {}ms", endTime - startTime);
        assertEquals(balance, result.getBalance());
    }

    @Test
    @DisplayName("10건 동시 충전 일때 정상적으로 충전이 되는지 체크")
    void chargeTest01() throws Exception {
        // given
        long memberId = 1L;
        long accountId = 1L;
        long amount = 1000L;

        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            final int index = i + 1;
            executorService.execute(() -> {
                long thisAmount = amount * index;
                try {
                    accountService.chargeAccount(new AccountDomainDto.ChargeAccountCommand(accountId, thisAmount));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();

        // then
        long balance = 200000L;
        for (int i = 1; i < 11; i++) {
            balance += (1000L * i);
        }

        Account result = accountJpaRepository.getAccountById(accountId);

        log.info("time : {}ms", endTime - startTime);
        assertEquals(balance, result.getBalance());
    }

    @Test
    @DisplayName("10건 동시 충전, 결제 할때 정상적으로 되는지 체크")
    void chargeAndPaymentTest01() throws Exception {
        // given
        long memberId = 1L;
        long accountId = 1L;
        long amount = 1000L;

        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < threadCount + 1; i++) {
            final int index = i;
            executorService.execute(() -> {
                long thisAmount = amount * index;
                if (index % 2 == 0) {
                    try {
                        accountService.chargeAccount(new AccountDomainDto.ChargeAccountCommand(accountId, thisAmount));
                    } finally {
                        countDownLatch.countDown();
                    }
                } else {
                    try {
                        accountService.paymentAccount(new AccountDomainDto.PaymentAccountCommand(accountId, thisAmount));
                    } finally {
                        countDownLatch.countDown();
                    }
                }

            });
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();

        // then
        long balance = 200000L;
        for (int i = 1; i < 11; i++) {
            if (i % 2 == 0) {
                balance += (1000L * i);
            } else {
                balance -= (1000L * i);
            }
        }

        Account result = accountJpaRepository.getAccountById(accountId);

        log.info("time : {}ms", endTime - startTime);
        assertEquals(balance, result.getBalance());
    }

}
