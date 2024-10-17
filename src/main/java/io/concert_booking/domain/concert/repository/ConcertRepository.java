package io.concert_booking.domain.concert.repository;

import io.concert_booking.domain.concert.entity.Concert;

import java.util.List;

public interface ConcertRepository {

    Concert save(Concert concert);

    Concert getConcertById(long concertId);

    List<Concert> getAllConcertByPerformerId(long performerId);

}
