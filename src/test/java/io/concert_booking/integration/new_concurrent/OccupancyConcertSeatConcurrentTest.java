package io.concert_booking.integration.new_concurrent;

import io.concert_booking.domain.concert.dto.ConcertSeatDomainDto;
import io.concert_booking.domain.concert.entity.ConcertSeat;
import io.concert_booking.domain.concert.entity.SeatStatus;
import io.concert_booking.domain.concert.service.ConcertSeatService;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import io.concert_booking.interfaces.scheduler.ConcertScheduler;
import io.concert_booking.interfaces.scheduler.QueueScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OccupancyConcertSeatConcurrentTest {

    @Autowired
    ConcertSeatService concertSeatService;

    @Autowired
    ConcertSeatJpaRepository concertSeatJpaRepository;

    @MockBean
    ConcertScheduler concertScheduler;

    @MockBean
    QueueScheduler queueScheduler;

    Logger log = LoggerFactory.getLogger(OccupancyConcertSeatConcurrentTest.class);

    @BeforeEach
    void setUp() {
        ConcertSeat save = concertSeatJpaRepository.save(new ConcertSeat(1L, 1, SeatStatus.OPEN));
        System.out.println(save.getConcertSeatId());
    }

    @Test
    @DisplayName("좌석 점유 동시성 테스트")
    void occupyConcertSeatTest02() throws Exception {
        // given
        long concertSeatId = 1L;

        // when
        ConcertSeatDomainDto.OccupancySeatCommand command = new ConcertSeatDomainDto.OccupancySeatCommand(concertSeatId, 1);
        concertSeatService.occupancySeat(command);

    }

    @Test
    @DisplayName("좌석 점유 동시성 테스트")
    void occupyConcertSeatTest01() throws Exception {
        // given
        long concertSeatId = 1L;

        final List<Long> failList = new ArrayList<>();
        final List<Long> successList = new ArrayList<>();

        // when
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < threadCount + 1; i++) {
            final int index = i;
            ConcertSeatDomainDto.OccupancySeatCommand command = new ConcertSeatDomainDto.OccupancySeatCommand(concertSeatId, index);
            executorService.execute(() -> {
                try {
                    concertSeatService.occupancySeat(command);
                    successList.add((long) index);
                } catch (Exception e) {
                    failList.add((long) index);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();

        ConcertSeat concertSeatByMemberId = concertSeatJpaRepository.getConcertSeatByMemberId(successList.getFirst());

        log.info("time : {}ms", endTime - startTime);
        // then
        assertAll(() -> {
            assertNotNull(concertSeatByMemberId);
            assertEquals(successList.size(), 1);
            assertEquals(successList.getFirst(), concertSeatByMemberId.getMemberId());
        });

    }
}
