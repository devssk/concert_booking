package io.concert_booking.interfaces.account;

public class AccountDto {

    public record ChargeRequest(
            Long memberId,
            Long amount
    ) {}

    public record ChargeResponse(
            Long balance
    ) {}

    public record PaymentRequest(
            String token,
            Long concertInfoId,
            Long concertSeatId,
            Long amount
    ) {}

    public record PaymentResponse(
            String concertName,
            String concertDate,
            Integer concertSeatNumber,
            Long paymentAmount
    ) {}

    public record AccountBalanceResponse(
            Long balance
    ) {}

}
