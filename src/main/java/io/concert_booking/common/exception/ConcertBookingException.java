package io.concert_booking.common.exception;

public class ConcertBookingException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public ConcertBookingException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ConcertBookingException(ErrorCode errorCode, String suffix) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage() + ", " + suffix;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return this.message;
    }
}
