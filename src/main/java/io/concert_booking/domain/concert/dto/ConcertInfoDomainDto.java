package io.concert_booking.domain.concert.dto;

import java.time.LocalDate;

public class ConcertInfoDomainDto {

    public record RegisterConcertInfoCommand(
            long concertId,
            LocalDate concertDate,
            int capacity
    ) {}

    public record RegisterConcertInfoInfo(
            long concertInfoId
    ) {}

    public record GetConcertInfoInfo(
            long concertInfoId,
            long concertId,
            String concertDate
    ) {}

    public record GetConcertInfoListInfo(
            long concertInfoId,
            long concertId,
            String concertDate
    ) {}

}
