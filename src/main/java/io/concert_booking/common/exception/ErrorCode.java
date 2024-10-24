package io.concert_booking.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    TOKEN_CIPHER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 cipher 에러"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰"),
    VALID_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    NOT_FOUND_ENTITY(HttpStatus.BAD_REQUEST, "해당 ID로 찾을 수 없음"),
    NOT_FOUND_QUEUE(HttpStatus.BAD_REQUEST, "대기열에서 찾을 수 없음"),
    OCCUPANCY_SEAT(HttpStatus.BAD_REQUEST, "이미 결제 대기중인 좌석"),
    NOT_ENTER_TOKEN(HttpStatus.FORBIDDEN, "대기열 통과되지 않은 토큰"),
    NOT_OCCUPANCY_TOKEN(HttpStatus.FORBIDDEN, "좌석을 점유하지 않은 토큰"),
    EXPIRED_OCCUPANCY_SEAT(HttpStatus.BAD_REQUEST, "좌석 점유 시간 만료"),
    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액 부족")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
