package io.concert_booking.interfaces.scheduler;

import io.concert_booking.application.concert.ConcertFacade;
import io.concert_booking.domain.concert.service.ConcertInfoService;
import io.concert_booking.domain.concert.service.ConcertSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final ConcertSeatService concertSeatService;
    private final ConcertInfoService concertInfoService;
    private final ConcertFacade concertFacade;

    @Scheduled(fixedDelay = 60000)
    public void occupancySeatCheckAndCleanScheduler() {
        concertSeatService.occupancySeatCheckAndClean();
    }

    @Scheduled(fixedDelay = 500)
    public void updateConcertSeatRedis() {
        LocalDate now = LocalDate.now();
        List<Long> concertInfoIdList = concertInfoService.getConcertInfoIdListByDate(now);
        concertFacade.updateConcertSeatForRedis(concertInfoIdList);
    }


}
