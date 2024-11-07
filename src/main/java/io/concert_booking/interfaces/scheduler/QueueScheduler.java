package io.concert_booking.interfaces.scheduler;

import io.concert_booking.domain.concert.service.ConcertInfoService;
import io.concert_booking.domain.queue.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueScheduler {

    private final QueueService queueService;
    private final ConcertInfoService concertInfoService;

    @Scheduled(fixedDelay = 5000)
    public void passQueueScheduler() {
        int passCount = 50;
        List<Long> todayConcertIdList = concertInfoService.getAllCocnertIdByConcertDate(LocalDate.now());
        for (Long concertId : todayConcertIdList) {
            queueService.passQueue(concertId, passCount);
        }
    }

}
