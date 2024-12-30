package io.concert_booking.infrastructure.concert.jpa;

import io.concert_booking.domain.concert.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformerJpaRepository extends JpaRepository<Performer, Long> {
}
