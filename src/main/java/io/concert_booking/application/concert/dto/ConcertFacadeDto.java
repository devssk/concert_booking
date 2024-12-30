package io.concert_booking.application.concert.dto;

public class ConcertFacadeDto {

    public record GetConcertInfoResult(
            long concertId,
            long concertInfoId,
            String concertName,
            String concertDate
    ) {}

    public record GetConcertSeatListResult(
            long concertSeatId,
            long concertInfoId,
            int seatNumber,
            String seatStatus,
            String createdAt,
            String updatedAt
    ) {}

    public record OccupancyConcertSeatCriteria(
            String token,
            long concertSeatId
    ) {}

    public record OccupancyConcertSeatResult(
            long concertSeatId,
            long memberId,
            String seatStatus
    ) {}

}
