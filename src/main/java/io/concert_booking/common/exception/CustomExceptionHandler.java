package io.concert_booking.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConcertBookingException.class)
    public ResponseEntity<ResponseBody> handleException(ConcertBookingException e) {
        log.warn(e.getErrorCode().name(), e.getErrorCode().getStatus(), e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ResponseBody(e.getMessage(), e.getErrorCode().name()));
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ResponseBody> handleException(ObjectOptimisticLockingFailureException e) {
        log.warn(ErrorCode.OCCUPANCY_SEAT.name(), ErrorCode.OCCUPANCY_SEAT.getStatus(), ErrorCode.OCCUPANCY_SEAT.getMessage());
        return ResponseEntity
                .status(ErrorCode.OCCUPANCY_SEAT.getStatus())
                .body(new ResponseBody(ErrorCode.OCCUPANCY_SEAT.getMessage(), ErrorCode.OCCUPANCY_SEAT.name()));
    }

}
