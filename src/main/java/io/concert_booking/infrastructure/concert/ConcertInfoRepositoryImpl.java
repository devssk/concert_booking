package io.concert_booking.infrastructure.concert;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.concert.entity.ConcertInfo;
import io.concert_booking.domain.concert.repository.ConcertInfoRepository;
import io.concert_booking.infrastructure.concert.jpa.ConcertInfoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConcertInfoRepositoryImpl implements ConcertInfoRepository {

    private final ConcertInfoJpaRepository concertInfoJpaRepository;

    @Transactional
    @Override
    public ConcertInfo save(ConcertInfo concertInfo) {
        return concertInfoJpaRepository.save(concertInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public ConcertInfo getConcertInfoById(long concertInfoId) {
        Optional<ConcertInfo> find = concertInfoJpaRepository.findById(concertInfoId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_ENTITY);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConcertInfo> getAllConcertInfoByConcertId(long concertId) {
        return concertInfoJpaRepository.getAllConcertInfoByConcertId(concertId);
    }
}
