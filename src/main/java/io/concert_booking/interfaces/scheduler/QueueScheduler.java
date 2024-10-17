package io.concert_booking.interfaces.scheduler;

import io.concert_booking.domain.queue.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueScheduler {

    private final QueueService queueService;

    @Scheduled(fixedDelay = 60000)
    public void passQueueScheduler() {
        int passCount = 50;
        List<Long> queueConcertIdList = queueService.getQueueConcertIdList();
        for (Long concertId : queueConcertIdList) {
            queueService.passQueue(concertId, passCount);
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void getOutQueueScheduler() {
        int expiredMinute = 20;
        queueService.getOutQueue(expiredMinute);
    }

}
