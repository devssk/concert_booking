package io.concert_booking.interfaces.scheduler;

import io.concert_booking.domain.concert.service.ConcertSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final ConcertSeatService concertSeatService;

    @Scheduled(fixedDelay = 60000)
    public void occupancySeatCheckAndCleanScheduler() {
        concertSeatService.occupancySeatCheckAndClean();
    }

}
