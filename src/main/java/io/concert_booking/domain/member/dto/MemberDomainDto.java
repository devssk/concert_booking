package io.concert_booking.domain.member.dto;

public class MemberDomainDto {

    public record RegisterMemberCommand(
            String memberName
    ) {}

    public record RegisterMemberInfo(
            long memberId
    ) {}

    public record GetMemberInfo(
            long memberId,
            String memberName
    ) {}

}
