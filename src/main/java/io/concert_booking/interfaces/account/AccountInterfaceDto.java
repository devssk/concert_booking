package io.concert_booking.interfaces.account;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;

public class AccountInterfaceDto {

    public record ChargeRequest(
            Long accountId,
            Long amount
    ) {
        public void validate() {
            if (accountId == null || amount == null) {
                String check = accountId == null ? "accountId" : "amount";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
            }
            if (accountId <= 0 || amount <= 0) {
                String check = accountId <= 0 ? "accountId" : "amount";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
            }
        }
    }

    public record ChargeResponse(
            long accountId,
            long amount,
            long balance
    ) {}

    public record PaymentRequest(
            Long concertSeatId,
            Long amount
    ) {
        public void validate() {
            if (concertSeatId == null || amount == null) {
                String check = concertSeatId == null ? "concertSeatId" : "amount";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
            }
            if (concertSeatId <= 0 || amount <= 0) {
                String check = concertSeatId <= 0 ? "concertSeatId" : "amount";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
            }
        }
    }

    public record PaymentResponse(
            String concertName,
            String concertDate,
            int concertSeatNumber,
            long paymentAmount
    ) {}

    public record AccountBalanceResponse(
            long balance
    ) {}

}
