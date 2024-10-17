package io.concert_booking.interfaces.account;

import io.concert_booking.interfaces.exception.ValidException;

public class AccountInterfaceDto {

    public record ChargeRequest(
            Long accountId,
            Long amount
    ) {
        public ChargeRequest {
            if (accountId == null || amount == null) {
                String check = accountId == null ? "accountId" : "amount";
                throw new ValidException(check + "를 입력해 주세요.");
            }
            if (accountId <= 0 || amount <= 0) {
                String check = accountId <= 0 ? "accountId" : "amount";
                throw new ValidException(check + "의 올바른 범위를 입력해 주세요.");
            }
        }
    }

    public record ChargeResponse(
            long accountId,
            long amount,
            long balance
    ) {}

    public record PaymentRequest(
            String token,
            Long concertSeatId,
            Long amount
    ) {
        public PaymentRequest {
            if (token == null || token.isEmpty() ||  concertSeatId == null || amount == null) {
                String check = token == null ? "token" : token.isEmpty() ? "token" : concertSeatId == null ? "concertSeatId" : "amount";
                throw new ValidException(check + "를 입력해 주세요.");
            }
            if (concertSeatId <= 0 || amount <= 0) {
                String check = concertSeatId <= 0 ? "concertSeatId" : "amount";
                throw new ValidException(check + "의 올바른 범위를 입력해 주세요.");
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
