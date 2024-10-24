package io.concert_booking.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

}
