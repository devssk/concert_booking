package io.concert_booking.infrastructure.concert.jpa;

import io.concert_booking.domain.concert.entity.ConcertSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long>, ConcertSeatQuerydslRepository {
    List<ConcertSeat> getAllConcertSeatByConcertInfoId(Long concertInfoId);

    ConcertSeat getConcertSeatByMemberId(Long memberId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ConcertSeat getConcertSeatByConcertSeatId(Long concertSeatId);
}
