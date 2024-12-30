package io.concert_booking.infrastructure.concert;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.concert.entity.Concert;
import io.concert_booking.domain.concert.repository.ConcertRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Concert save(Concert concert) {
        return concertJpaRepository.save(concert);
    }

    @Override
    public Concert getConcertById(long concertId) {
        Optional<Concert> find = concertJpaRepository.findById(concertId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_ENTITY);
        }
    }

    @Override
    public List<Concert> getAllConcertByPerformerId(long performerId) {
        return concertJpaRepository.getAllConcertByPerformerId(performerId);
    }
}
