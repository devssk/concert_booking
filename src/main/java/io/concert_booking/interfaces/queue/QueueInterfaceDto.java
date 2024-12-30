package io.concert_booking.interfaces.queue;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;

public class QueueInterfaceDto {

    public record IssueTokenRequest(
            Long memberId,
            Long concertInfoId
    ) {
        public void validate() {
            if (memberId == null || concertInfoId == null) {
                String check = memberId == null ? "memberId" : "concertInfoId";
                throw new ConcertBookingException(ErrorCode.VALID_ERROR, check);
            }
            if (memberId <= 0 || concertInfoId <= 0) {
                String check = memberId <= 0 ? "memberId" : "concertInfoId";
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
            int backQueue
    ) {}

}
