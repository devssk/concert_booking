package io.concert_booking.domain.concert.service;

import io.concert_booking.domain.concert.dto.ConcertDomainDto;
import io.concert_booking.domain.concert.entity.Concert;
import io.concert_booking.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertDomainDto.RegisterConcertInfo registerConcert(ConcertDomainDto.RegisterConcertCommand command) {
        Concert concert = new Concert(command.performerId(), command.concertName());
        Concert saveConcert = concertRepository.save(concert);
        return new ConcertDomainDto.RegisterConcertInfo(saveConcert.getConcertId());
    }

    public ConcertDomainDto.GetConcertInfo getConcert(long concertId) {
        Concert getConcert = concertRepository.getConcertById(concertId);
        return new ConcertDomainDto.GetConcertInfo(getConcert.getConcertId(), getConcert.getPerformerId(), getConcert.getConcertName());
    }

    public List<ConcertDomainDto.GetConcertListByPerformerIdInfo> getConcertListByPerformerId(long performerId) {
        List<Concert> getConcertList = concertRepository.getAllConcertByPerformerId(performerId);
        return getConcertList.stream().map(concert -> new ConcertDomainDto.GetConcertListByPerformerIdInfo(
                concert.getConcertId(),
                concert.getPerformerId(),
                concert.getConcertName()
        )).toList();
    }

}
