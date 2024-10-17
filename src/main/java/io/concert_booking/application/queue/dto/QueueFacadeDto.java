package io.concert_booking.application.queue.dto;

public class QueueFacadeDto {

    public record RegisterQueueCriteria(
            long memberId,
            long concertInfoId
    ) {}

    public record RegisterQueueResult(
            String token
    ) {}

    public record GetMyQueueNumberResult(
            int frontQueue,
            int myQueueNumber,
            int backQueue,
            String queueStatus
    ) {}

}
