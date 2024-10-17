package io.concert_booking.interfaces.concert;

import io.concert_booking.interfaces.exception.ValidException;

public class ConcertInterfaceDto {

    public record ConcertInfoResponse(
            long concertId,
            long concertInfoId,
            String concertName,
            String concertDate
    ) {}

    public record ConcertSeatResponse(
            long concertInfoId,
            long concertSeatId,
            int concertSeatNumber,
            String concertSeatStatus
    ) {}

    public record OccupancySeatRequest(
            String token,
            Long concertSeatId
    ) {
        public OccupancySeatRequest {
            if (token == null || token.isEmpty()) {
                throw new ValidException("token을 입력해 주세요.");
            }
            if (concertSeatId == null || concertSeatId <= 0) {
                String message = concertSeatId == null ? "concertSeatId를 입력해 주세요." : "concertSeatId의 올바른 범위를 입력해 주세요.";
                throw new ValidException(message);
            }
        }
    }

    public record OccupancySeatResponse(
            long concertSeatId
    ) {}

    public record BookingInfoResponse(
            long bookingId,
            String concertName,
            String concertDate,
            int concertSeatNumber,
            long paymentAmount
    ) {}

    public record BookingInfoListResponse(
            long bookingId,
            String concertName,
            String concertDate,
            int concertSeatNumber,
            long paymentAmount
    ) {}

}
