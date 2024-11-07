package io.concert_booking.domain.queue.dto;

import io.concert_booking.domain.queue.entity.QueueStatus;

public class QueueDomainDto {

    public record RegisterQueueCommand(
            long concertId,
            long memberId
    ) {}

    public record RegisterQueueInfo(
            String queueId
    ) {}

    public record UpdateQueueStatusCommand(
            long concertId,
            long memberId,
            QueueStatus queueStatus
    ) {}

    public record DeleteQueueCommand(
            long concertId,
            long memberId
    ) {}

    public record ExistsWaitQueueCommand(
            long concertId,
            long memberId
    ) {}

    public record ExistsPassQueueCommand(
            long concertId,
            long memberId
    ) {}

    public record GetPassQueueStatusCommand(
            long concertId,
            long memberId
    ) {}

    public record GetPassQueueStatusInfo(
            String queueStatus
    ) {}

    public record GetMyQueueRankingCommand(
            long concertId,
            long memberId
    ) {}

    public record GetQueueMemberIdInfo(
            long memberId
    ) {}

    public record GetQueueInfo(
            long queueId,
            long concertId,
            String queueStatus,
            String createdAt
    ) {}


}
