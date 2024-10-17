package io.concert_booking.integration.usecase;

import io.concert_booking.application.queue.QueueFacade;
import io.concert_booking.application.queue.dto.QueueFacadeDto;
import io.concert_booking.domain.concert.entity.Concert;
import io.concert_booking.domain.concert.entity.ConcertInfo;
import io.concert_booking.domain.concert.entity.Performer;
import io.concert_booking.domain.member.entity.Member;
import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.TokenService;
import io.concert_booking.infrastructure.concert.jpa.ConcertInfoJpaRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertJpaRepository;
import io.concert_booking.infrastructure.concert.jpa.PerformerJpaRepository;
import io.concert_booking.infrastructure.member.jpa.MemberJpaRepository;
import io.concert_booking.infrastructure.queue.jpa.QueueJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QueueIntegrationTest {

    @Autowired
    QueueFacade queueFacade;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    ConcertJpaRepository concertJpaRepository;
    @Autowired
    PerformerJpaRepository performerJpaRepository;
    @Autowired
    ConcertInfoJpaRepository concertInfoJpaRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    QueueJpaRepository queueJpaRepository;

    @BeforeEach
    void setUp() {
        memberJpaRepository.save(new Member("유애나"));
        Performer performer = performerJpaRepository.save(new Performer("아이유"));
        Concert concert = concertJpaRepository.save(new Concert(performer.getPerformerId(), "2024 IU CONCERT HER"));
        concertInfoJpaRepository.save(new ConcertInfo(concert.getConcertId(), LocalDate.of(2024, 11, 1), 50));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
        queueJpaRepository.save(new Queue(concert.getConcertId(), QueueStatus.WAIT));
    }

    @Test
    @Rollback
    @DisplayName("정상 토큰 발급 및 대기열 등록")
    void registerQueueTest() throws Exception {
        // given
        long concertInfoId = 1L;
        long memberId = 1L;
        QueueFacadeDto.RegisterQueueCriteria criteria = new QueueFacadeDto.RegisterQueueCriteria(memberId, concertInfoId);

        // when
        QueueFacadeDto.RegisterQueueResult result = queueFacade.registerQueue(criteria);
        Map<String, Long> payload = tokenService.decodeToken(result.token());

        Queue queue = queueJpaRepository.findById(payload.get("queueId")).get();

        // then
        assertAll(() -> {
            assertEquals(queue.getQueueStatus(), QueueStatus.WAIT);
        });
    }

    @Test
    @Rollback
    @DisplayName("나의 대기열 조회")
    void getMyQueueNumberTest() {
        // given
        long concertId = 1L;
        long concertInfoId = 1L;
        long memberId = 1L;
        Queue queue = queueJpaRepository.save(new Queue(concertId, QueueStatus.WAIT));
        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", concertId);
        payload.put("concertInfoId", concertInfoId);
        payload.put("memberId", memberId);
        payload.put("queueId", queue.getQueueId());
        String token = tokenService.issueToken(payload);

        // when
        QueueFacadeDto.GetMyQueueNumberResult result = queueFacade.getMyQueueNumber(token);

        // then
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(9, result.frontQueue());
            assertEquals(10, result.myQueueNumber());
            assertEquals(0, result.backQueue());
            assertEquals(QueueStatus.WAIT.name(), result.queueStatus());
        });
    }

}
