package io.concert_booking.application.account.dto;

public class AccountFacadeDto {

    public record PaymentConcertCriteria(
            String token,
            long concertSeatId,
            long amount
    ) {}

    public record PaymentConcertResult(
            long bookingId,
            String concertName,
            String concertDate,
            int concertSeatNumber,
            long paymentAmount
    ) {}

    public record RegisterAccountCriteria(
            long memberId
    ) {}

    public record ChargeAccountCriteria(
            long accountId,
            long amount
    ) {}

}
