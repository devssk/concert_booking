package io.concert_booking.integration.concurrent;

import io.concert_booking.domain.concert.dto.ConcertSeatDomainDto;
import io.concert_booking.domain.concert.entity.ConcertSeat;
import io.concert_booking.domain.concert.entity.SeatStatus;
import io.concert_booking.domain.concert.service.ConcertSeatService;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OccupancySeatConcurrentTest {

    @Autowired
    ConcertSeatService concertSeatService;

    @Autowired
    ConcertSeatJpaRepository concertSeatJpaRepository;

    @BeforeEach
    void setUp() {
        concertSeatJpaRepository.save(new ConcertSeat(1L, 1, SeatStatus.OPEN));
    }

    @Test
    void occupyConcertSeatTest01() throws Exception {
        // given
        long concertSeatId = 1L;
        long memberId = 1L;
        ConcertSeatDomainDto.OccupancySeatCommand command = new ConcertSeatDomainDto.OccupancySeatCommand(concertSeatId, memberId);

        final List<String> list = new ArrayList<>();

        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    concertSeatService.occupancySeat(command);
                    System.out.println(index + "번째 실행");
                } catch (Exception e) {
                    list.add(index + "번째");
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        assertEquals(threadCount - 1, list.size());

    }

}
