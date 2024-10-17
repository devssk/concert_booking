package io.concert_booking.infrastructure.concert;

import io.concert_booking.domain.concert.entity.Booking;
import io.concert_booking.domain.concert.repository.BookingRepository;
import io.concert_booking.infrastructure.concert.jpa.BookingJpaRepository;
import io.concert_booking.infrastructure.exception.EntityRowNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJpaRepository bookingJpaRepository;

    @Transactional
    @Override
    public Booking save(Booking booking) {
        return bookingJpaRepository.save(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public Booking getBookingById(long bookingId) {
        Optional<Booking> find = bookingJpaRepository.findById(bookingId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new EntityRowNotFoundException("해당 예약을 찾을 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Booking> getAllBookingByMemberId(long memberId) {
        return bookingJpaRepository.getAllBookingByMemberId(memberId);
    }


}
