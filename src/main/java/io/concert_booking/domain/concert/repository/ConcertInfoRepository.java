package io.concert_booking.domain.concert.repository;

import io.concert_booking.domain.concert.entity.ConcertInfo;

import java.util.List;

public interface ConcertInfoRepository {

    ConcertInfo save(ConcertInfo concertInfo);

    ConcertInfo getConcertInfoById(long concertInfoId);

    List<ConcertInfo> getAllConcertInfoByConcertId(long concertId);

}
