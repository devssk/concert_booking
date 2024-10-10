package io.concert_booking.interfaces;

public record ResponseDto(
        ResponseCode code,
        Object data
) {
}
