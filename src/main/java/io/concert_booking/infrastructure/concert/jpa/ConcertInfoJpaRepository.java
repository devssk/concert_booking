package io.concert_booking.infrastructure.concert.jpa;

import io.concert_booking.domain.concert.entity.ConcertInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertInfoJpaRepository extends JpaRepository<ConcertInfo, Long>, ConcertInfoQuerydslRepository {
    List<ConcertInfo> getAllConcertInfoByConcertId(Long concertId);
}
