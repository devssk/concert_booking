package io.concert_booking.infrastructure.queue.jpa;

import io.concert_booking.domain.queue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueJpaRepository extends JpaRepository<Queue, Long>, QueueQuerydslRepository {
}
