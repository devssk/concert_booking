package io.concert_booking.infrastructure.member.jpa;

import io.concert_booking.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
