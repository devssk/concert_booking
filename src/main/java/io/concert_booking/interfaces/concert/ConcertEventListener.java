package io.concert_booking.interfaces.concert;

import io.concert_booking.domain.concert.dto.BookingDomainDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ConcertEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void concertBookingHandle(BookingDomainDto.BookingEvent event) {
        try {
            log.info("ConcertEventListener received event: {}", event);
            Thread.sleep(3000L);
            log.info("ConcertEventListener api 호출 성공");
        } catch (InterruptedException e) {
            log.error("api 호출 에러 발생");
        }
    }

}
