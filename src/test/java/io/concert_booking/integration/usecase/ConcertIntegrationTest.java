package io.concert_booking.integration.usecase;
import io.concert_booking.application.concert.ConcertFacade;
import io.concert_booking.application.concert.dto.ConcertFacadeDto;
import io.concert_booking.domain.concert.entity.*;
import io.concert_booking.domain.member.entity.Member;
import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.TokenService;
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
public class ConcertIntegrationTest {

    @Autowired
    ConcertFacade concertFacade;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    PerformerJpaRepository performerJpaRepository;
    @Autowired
    ConcertJpaRepository concertJpaRepository;
    @Autowired
    ConcertInfoJpaRepository concertInfoJpaRepository;
    @Autowired
    QueueJpaRepository queueJpaRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    ConcertSeatJpaRepository concertSeatJpaRepository;

    @BeforeEach
    void setUp() {
        memberJpaRepository.save(new Member("유애나"));
        Performer performer = performerJpaRepository.save(new Performer("아이유"));
        Concert concert = concertJpaRepository.save(new Concert(performer.getPerformerId(), "2024 IU CONCERT HER"));
        ConcertInfo concertInfo = concertInfoJpaRepository.save(new ConcertInfo(concert.getConcertId(), LocalDate.of(2024, 11, 1), 50));
        concertSeatJpaRepository.save(new ConcertSeat(concertInfo.getConcertInfoId(), 1, SeatStatus.OPEN));
    }

    @Test
    @DisplayName("좌석 임시 점유")
    void occupancyConcertSeatTest() {
        // given
        long concertId = 1L;
        long concertInfoId = 1L;
        long memberId = 1L;
        Queue queue = queueJpaRepository.save(new Queue(concertId, QueueStatus.ENTER));
        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", concertId);
        payload.put("concertInfoId", concertInfoId);
        payload.put("memberId", memberId);
        payload.put("queueId", queue.getQueueId());
        String token = tokenService.issueToken(payload);
        ConcertFacadeDto.OccupancyConcertSeatCriteria criteria = new ConcertFacadeDto.OccupancyConcertSeatCriteria(token, concertId);

        // when
        ConcertFacadeDto.OccupancyConcertSeatResult result = concertFacade.OccupancyConcertSeat(criteria);

        // then
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(memberId, result.memberId());
            assertEquals(SeatStatus.OCCUPANCY.name(), result.seatStatus());
        });
    }

}
