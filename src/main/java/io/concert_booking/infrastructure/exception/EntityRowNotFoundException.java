package io.concert_booking.infrastructure.exception;

public class EntityRowNotFoundException extends RuntimeException {
    public EntityRowNotFoundException(String message) {
        super(message);
    }
}
