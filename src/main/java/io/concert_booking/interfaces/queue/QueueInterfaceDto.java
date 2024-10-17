package io.concert_booking.interfaces.queue;

import io.concert_booking.interfaces.exception.ValidException;

public class QueueInterfaceDto {

    public record IssueTokenRequest(
            Long userId,
            Long concertInfoId
    ) {
        public IssueTokenRequest {
            if (userId == null || concertInfoId == null) {
                String check = userId == null ? "userId" : "concertInfoId";
                throw new ValidException(check + "를 입력해 주세요.");
            }
            if (userId <= 0 || concertInfoId <= 0) {
                String check = userId <= 0 ? "userId" : "concertInfoId";
                throw new ValidException(check + "의 올바른 범위를 입력해 주세요.");
            }
        }
    }

    public record IssueTokenResponse(
            String token
    ) {}

    public record WaitingNumberResponse(
            int frontQueue,
            int myQueueNumber,
            int backQueue,
            String queueStatus
    ) {}

}
