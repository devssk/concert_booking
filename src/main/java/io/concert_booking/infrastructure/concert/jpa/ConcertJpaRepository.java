package io.concert_booking.infrastructure.concert.jpa;

import io.concert_booking.domain.concert.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
    List<Concert> getAllConcertByPerformerId(Long performerId);
}
