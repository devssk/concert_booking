package io.concert_booking.domain.concert.dto;

import io.concert_booking.domain.concert.entity.SeatStatus;

import java.time.LocalDateTime;

public class ConcertSeatDomainDto {

    public record RegisterConcertSeatCommand(
            long concertInfoId,
            int seatNumber
    ) {}

    public record RegisterConcertSeatInfo(
            long concertSeatId
    ) {}

    public record UpdateConcertSeatStatusCommand(
            long concertSeatId,
            SeatStatus seatStatus
    ) {}

    public record UpdateConcertSeatStatusInfo(
            long concertSeatId
    ) {}

    public record OccupancySeatCommand(
            long concertSeatId,
            long memberId
    ) {}

    public record OccupancySeatInfo(
            long concertSeatId,
            long memberId,
            String seatStatus
    ) {}

    public record GetConcertSeatInfo(
            long concertSeatId,
            long concertInfoId,
            int seatNumber,
            String seatStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    public record GetConcertSeatListInfo(
            long concertSeatId,
            long concertInfoId,
            int seatNumber,
            String seatStatus,
            String createdAt,
            String updatedAt
    ) {}

}
