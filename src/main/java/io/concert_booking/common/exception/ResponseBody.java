package io.concert_booking.common.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseBody{

    private final String message;
    private final String error;
    private final String timestamp;

    public ResponseBody(String message, String error) {
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
