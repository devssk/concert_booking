package io.concert_booking.infrastructure.concert;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.concert.entity.Performer;
import io.concert_booking.domain.concert.repository.PerformerRepository;
import io.concert_booking.infrastructure.concert.jpa.PerformerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PerformerRepositoryImpl implements PerformerRepository {

    private final PerformerJpaRepository performerJpaRepository;

    @Transactional
    @Override
    public Performer save(Performer performer) {
        return performerJpaRepository.save(performer);
    }

    @Transactional(readOnly = true)
    @Override
    public Performer getPerformerById(long performerId) {
        Optional<Performer> find = performerJpaRepository.findById(performerId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_ENTITY);
        }
    }
}
