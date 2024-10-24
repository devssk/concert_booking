package io.concert_booking.interfaces.queue;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;

public class QueueInterfaceDto {

    public record IssueTokenRequest(
            Long userId,
            Long concertInfoId
    ) {
        public void validate() {
            if (userId == null || concertInfoId == null) {
                String check = userId == null ? "userId" : "concertInfoId";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
            }
            if (userId <= 0 || concertInfoId <= 0) {
                String check = userId <= 0 ? "userId" : "concertInfoId";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
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
