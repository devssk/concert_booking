package io.concert_booking.domain.account.dto;

public class AccountDomainDto {

    public record RegisterAccountCommand(
            long memberId
    ) {}

    public record RegisterAccountInfo(
            long accountId
    ) {}

    public record GetAccountByIdInfo(
            long accountId,
            long balance
    ) {}

    public record GetAccountByMemberIdInfo(
            long accountId,
            long balance
    ) {}

    public record ChargeAccountCommand(
            long accountId,
            long amount
    ) {}

    public record ChargeAccountInfo(
            long accountId,
            long amount,
            long balance
    ) {}

    public record PaymentAccountCommand(
            long accountId,
            long amount
    ) {}

    public record PaymentAccountInfo(
            long accountId,
            long accountHistoryId,
            long amount,
            long balance
    ) {}

}
