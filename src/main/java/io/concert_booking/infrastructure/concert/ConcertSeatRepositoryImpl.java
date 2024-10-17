package io.concert_booking.infrastructure.concert;

import io.concert_booking.domain.concert.entity.ConcertSeat;
import io.concert_booking.domain.concert.repository.ConcertSeatRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertSeatJpaRepository;
import io.concert_booking.infrastructure.exception.EntityRowNotFoundException;
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
            throw new EntityRowNotFoundException("해당 좌석을 찾을 수 없습니다.");
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
