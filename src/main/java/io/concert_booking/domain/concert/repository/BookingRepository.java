package io.concert_booking.domain.concert.repository;

import io.concert_booking.domain.concert.entity.Booking;

import java.util.List;

public interface BookingRepository {

    Booking save(Booking booking);

    Booking getBookingById(long bookingId);

    List<Booking> getAllBookingByMemberId(long memberId);

}
