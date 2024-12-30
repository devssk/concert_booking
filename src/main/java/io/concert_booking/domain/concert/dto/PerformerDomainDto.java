package io.concert_booking.domain.concert.dto;

public class PerformerDomainDto {

    public record RegisterPerformerCommand(
            String performerName
    ) {}

    public record RegisterPerformerInfo(
            long performerId
    ) {}

    public record GetPerformerInfo(
            long performerId,
            String performerName
    ) {}

}
