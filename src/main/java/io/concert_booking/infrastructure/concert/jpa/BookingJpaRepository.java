package io.concert_booking.infrastructure.concert.jpa;

import io.concert_booking.domain.concert.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingJpaRepository extends JpaRepository<Booking, Long> {
    List<Booking> getAllBookingByMemberId(Long memberId);
}
