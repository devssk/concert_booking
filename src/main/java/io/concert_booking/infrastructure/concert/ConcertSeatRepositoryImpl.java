package io.concert_booking.infrastructure.concert;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.concert.entity.ConcertSeat;
import io.concert_booking.domain.concert.repository.ConcertSeatRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    @Transactional
    @Override
    public ConcertSeat save(ConcertSeat concertSeat) {
        return concertSeatJpaRepository.save(concertSeat);
    }

    @Transactional(readOnly = true)
    @Override
    public ConcertSeat getConcertSeatById(long concertSeatId) {
        Optional<ConcertSeat> find = concertSeatJpaRepository.findById(concertSeatId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_ENTITY);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConcertSeat> getAllConcertSeatByConcertInfoId(long concertInfoId) {
        return concertSeatJpaRepository.getAllConcertSeatByConcertInfoId(concertInfoId);
    }

    @Transactional
    @Override
    public void updateConcertSeatStatusOccupancyExpired() {
        concertSeatJpaRepository.updateConcertSeatStatusOccupancyExpired();
    }
}
