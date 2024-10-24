package io.concert_booking.domain.concert.repository;

import io.concert_booking.domain.concert.entity.ConcertSeat;

import java.util.List;

public interface ConcertSeatRepository {

    ConcertSeat save(ConcertSeat concertSeat);

    ConcertSeat getConcertSeatById(long concertSeatId);

    ConcertSeat getConcertSeatByIdForUpdate(long concertSeatId);

    List<ConcertSeat> getAllConcertSeatByConcertInfoId(long concertInfoId);

    void updateConcertSeatStatusOccupancyExpired();

}
