package io.concert_booking.integration.usecase;

import io.concert_booking.application.account.AccountFacade;
import io.concert_booking.application.account.dto.AccountFacadeDto;
import io.concert_booking.domain.account.entity.Account;
import io.concert_booking.domain.concert.entity.*;
import io.concert_booking.domain.concert.service.ConcertSeatService;
import io.concert_booking.domain.member.entity.Member;
import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.TokenService;
import io.concert_booking.infrastructure.account.jpa.AccountJpaRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertInfoJpaRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertJpaRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import io.concert_booking.infrastructure.concert.jpa.PerformerJpaRepository;
import io.concert_booking.infrastructure.member.jpa.MemberJpaRepository;
import io.concert_booking.infrastructure.queue.jpa.QueueJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountIntegrationTest {

    @Autowired
    AccountFacade accountFacade;
    @Autowired
    TokenService tokenService;
    @Autowired
    QueueJpaRepository queueJpaRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    ConcertJpaRepository concertJpaRepository;
    @Autowired
    PerformerJpaRepository performerJpaRepository;
    @Autowired
    ConcertSeatService concertSeatService;
    @Autowired
    ConcertInfoJpaRepository concertInfoJpaRepository;
    @Autowired
    ConcertSeatJpaRepository concertSeatJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;

    @BeforeEach
    void setUp() {
        memberJpaRepository.save(new Member("유애나"));
        performerJpaRepository.save(new Performer("아이유"));
        concertJpaRepository.save(new Concert(1L, "2024 IU CONCERT HER"));
        concertInfoJpaRepository.save(new ConcertInfo(1L, LocalDate.of(2024, 11, 1), 50));
        concertSeatJpaRepository.save(new ConcertSeat(1L, 1, SeatStatus.OPEN));
        queueJpaRepository.save(new Queue(1L, QueueStatus.OCCUPANCY));
        accountJpaRepository.save(new Account(1L, 200000L));
    }

    @Test
    @DisplayName("정상 결제 및 예약 건")
    void paymentConcertTest01() {
        // given
        long concertId = 1L;
        long concertInfoId = 1L;
        long memberId = 1L;
        long queueId = 1L;
        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", concertId);
        payload.put("concertInfoId", concertInfoId);
        payload.put("memberId", memberId);
        payload.put("queueId", queueId);
        String token = tokenService.issueToken(payload);
        long concertSeatId = 1L;
        long amount = 169000L;
        AccountFacadeDto.PaymentConcertCriteria criteria = new AccountFacadeDto.PaymentConcertCriteria(token, concertSeatId, amount);

        // when
        AccountFacadeDto.PaymentConcertResult result = accountFacade.paymentConcert(criteria);

        // then
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(amount, result.paymentAmount());
        });
    }

}
