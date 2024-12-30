package io.concert_booking.domain.concert.dto;

public class ConcertDomainDto {

    public record RegisterConcertCommand(
            long performerId,
            String concertName
    ) {}

    public record RegisterConcertInfo(
            long concertId
    ) {}

    public record GetConcertInfo(
            long concertId,
            long performerId,
            String concertName
    ) {}

    public record GetConcertListByPerformerIdInfo(
            long concertId,
            long performerId,
            String concertName
    ) {}

}
