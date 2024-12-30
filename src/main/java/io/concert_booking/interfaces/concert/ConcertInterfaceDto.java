package io.concert_booking.interfaces.concert;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;

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
            Long concertSeatId
    ) {
        public void validate() {
            if (concertSeatId == null || concertSeatId <= 0) {
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, "concertSeatId");
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
