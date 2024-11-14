package io.concert_booking.infrastructure.concert;

import io.concert_booking.domain.concert.dto.BookingDomainDto;
import io.concert_booking.domain.concert.publisher.BookingPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingPublisherImpl implements BookingPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(BookingDomainDto.BookingEvent event) {
        eventPublisher.publishEvent(event);
    }
}
