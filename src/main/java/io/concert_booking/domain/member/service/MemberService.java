package io.concert_booking.domain.member.service;

import io.concert_booking.domain.member.dto.MemberDomainDto;
import io.concert_booking.domain.member.entity.Member;
import io.concert_booking.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDomainDto.RegisterMemberInfo registerMember(MemberDomainDto.RegisterMemberCommand command) {
        Member member = new Member(command.memberName());
        Member saveMember = memberRepository.save(member);
        return new MemberDomainDto.RegisterMemberInfo(saveMember.getMemberId());
    }

    public MemberDomainDto.GetMemberInfo getMember(long memberId) {
        Member getMember = memberRepository.getMemberById(memberId);
        return new MemberDomainDto.GetMemberInfo(getMember.getMemberId(), getMember.getMemberName());
    }

}
