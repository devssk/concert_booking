package io.concert_booking.domain.concert.publisher;

import io.concert_booking.domain.concert.dto.BookingDomainDto;

public interface BookingPublisher {
    void publish(BookingDomainDto.BookingEvent event);
}
