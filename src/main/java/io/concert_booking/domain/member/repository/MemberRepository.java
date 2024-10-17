package io.concert_booking.domain.member.repository;

import io.concert_booking.domain.member.entity.Member;

public interface MemberRepository {

    Member save(Member member);

    Member getMemberById(long memberId);

}
