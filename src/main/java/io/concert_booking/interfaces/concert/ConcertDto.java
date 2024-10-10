package io.concert_booking.interfaces.concert;

public class ConcertDto {

    public record ConcertInfoResponse(
            Long concertId,
            Long concertInfoId,
            String concertName,
            String concertDate
    ) {}

    public record ConcertSeatResponse(
            Long concertInfoId,
            Long concertSeatId,
            Integer concertSeatNumber,
            String concertSeatStatus
    ) {}

    public record BookingSeatRequest(
            String token,
            Long concertInfoId,
            Long concertSeatId
    ) {}

    public record BookingSeatResponse(
            Long concertSeatId,
            Integer expireMinute
    ) {}

    public record BookingInfoResponse(
            Long bookingId,
            String concertName,
            String concertDate,
            Integer concertSeatNumber,
            Long paymentAmount
    ) {}

    public record BookingInfoListResponse(
            Long bookingId,
            String concertName,
            String concertDate,
            Integer concertSeatNumber,
            Long paymentAmount
    ) {}

}
