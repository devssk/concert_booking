package io.concert_booking.infrastructure.member;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.member.entity.Member;
import io.concert_booking.domain.member.repository.MemberRepository;
import io.concert_booking.infrastructure.member.jpa.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Member getMemberById(long memberId) {
        Optional<Member> find = memberJpaRepository.findById(memberId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_ENTITY);
        }
    }
}
