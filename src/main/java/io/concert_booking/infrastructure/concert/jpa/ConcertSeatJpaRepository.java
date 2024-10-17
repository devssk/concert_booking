package io.concert_booking.infrastructure.concert.jpa;

import io.concert_booking.domain.concert.entity.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long>, ConcertSeatQuerydslRepository {
    List<ConcertSeat> getAllConcertSeatByConcertInfoId(Long concertInfoId);
}
