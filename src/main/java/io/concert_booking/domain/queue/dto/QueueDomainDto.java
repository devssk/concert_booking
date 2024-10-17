package io.concert_booking.domain.queue.dto;

import io.concert_booking.domain.queue.entity.QueueStatus;

public class QueueDomainDto {

    public record RegisterQueueCommand(
            long concertId
    ) {}

    public record RegisterQueueInfo(
            long queueId
    ) {}

    public record UpdateQueueStatusCommand(
            long queueId,
            QueueStatus queueStatus
    ) {}

    public record UpdateQueueStatusInfo(
            long queueId,
            String queueStatus
    ) {}

    public record GetQueueInfo(
            long queueId,
            long concertId,
            String queueStatus,
            String createdAt
    ) {}

    public record GetQueueListInfo(
            long queueId,
            long concertId,
            String queueStatus,
            String createdAt
    ) {}

}
