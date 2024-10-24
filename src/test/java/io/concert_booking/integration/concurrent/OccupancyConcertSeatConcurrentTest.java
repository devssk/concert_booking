package io.concert_booking.integration.concurrent;

import io.concert_booking.domain.concert.dto.ConcertSeatDomainDto;
import io.concert_booking.domain.concert.entity.ConcertSeat;
import io.concert_booking.domain.concert.entity.SeatStatus;
import io.concert_booking.domain.concert.service.ConcertSeatService;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OccupancyConcertSeatConcurrentTest {

    @Autowired
    ConcertSeatService concertSeatService;

    @Autowired
    ConcertSeatJpaRepository concertSeatJpaRepository;

    @BeforeEach
    void setUp() {
        ConcertSeat save = concertSeatJpaRepository.save(new ConcertSeat(1L, 1, SeatStatus.OPEN));
        System.out.println(save.getConcertSeatId());
    }

    @Test
    @DisplayName("좌석 점유 동시성 테스트")
    void occupyConcertSeatTest01() throws Exception {
        // given
        long concertSeatId = 1L;

        final List<Long> failList = new ArrayList<>();
        final List<Long> successList = new ArrayList<>();

        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
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

        ConcertSeat concertSeatByMemberId = concertSeatJpaRepository.getConcertSeatByMemberId(successList.getFirst());

        System.out.println(successList);
        System.out.println(failList);

        // then
        assertAll(() -> {
            assertNotNull(concertSeatByMemberId);
            assertEquals(successList.size(), 1);
            assertEquals(successList.getFirst(), concertSeatByMemberId.getMemberId());
        });

    }
}
