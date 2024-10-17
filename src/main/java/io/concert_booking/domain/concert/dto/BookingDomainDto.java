package io.concert_booking.domain.concert.dto;

public class BookingDomainDto {
    
    public record RegisterBookingCommand(
            long performerId,
            long concertId,
            long concertInfoId,
            long concertSeatId,
            long memberId,
            long accountHistoryId,
            String performerName,
            String concertName,
            String concertDate,
            int seatNumber,
            long paymentAmount,
            String memberName
    ) {}

    public record RegisterBookingInfo(
            long bookingId
    ) {}

    public record GetBookingInfo(
            long bookingId,
            long performerId,
            long concertId,
            long concertInfoId,
            long concertSeatId,
            long memberId,
            long accountHistoryId,
            String performerName,
            String concertName,
            String concertDate,
            int seatNumber,
            long paymentAmount,
            String memberName,
            String createdAt
    ) {}

    public record GetBookingListInfo(
            long bookingId,
            long performerId,
            long concertId,
            long concertInfoId,
            long concertSeatId,
            long memberId,
            long accountHistoryId,
            String performerName,
            String concertName,
            String concertDate,
            int seatNumber,
            long paymentAmount,
            String memberName,
            String createdAt
    ) {}
    
}
