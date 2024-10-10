package io.concert_booking.interfaces.queue;

public class QueueDto {

    public record IssueTokenRequest(
            Long userId,
            Long concertInfoId
    ) {}

    public record IssueTokenResponse(
            String token
    ) {}

    public record WaitingNumberResponse(
            Integer waitingNumber
    ) {}

}
