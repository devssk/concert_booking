package io.concert_booking.domain.concert.service;

import io.concert_booking.domain.concert.dto.ConcertInfoDomainDto;
import io.concert_booking.domain.concert.entity.ConcertInfo;
import io.concert_booking.domain.concert.repository.ConcertInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertInfoService {

    private final ConcertInfoRepository concertInfoRepository;

    public ConcertInfoDomainDto.RegisterConcertInfoInfo RegisterConcertInfo(ConcertInfoDomainDto.RegisterConcertInfoCommand command) {
        ConcertInfo concertInfo = new ConcertInfo(command.concertId(), command.concertDate(), command.capacity());
        ConcertInfo saveConcertInfo = concertInfoRepository.save(concertInfo);
        return new ConcertInfoDomainDto.RegisterConcertInfoInfo(saveConcertInfo.getConcertInfoId());
    }

    public ConcertInfoDomainDto.GetConcertInfoInfo getConcertInfo(long concertInfoId) {
        ConcertInfo getConcertInfo = concertInfoRepository.getConcertInfoById(concertInfoId);
        return new ConcertInfoDomainDto.GetConcertInfoInfo(
                getConcertInfo.getConcertInfoId(),
                getConcertInfo.getConcertId(),
                getConcertInfo.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public List<ConcertInfoDomainDto.GetConcertInfoListInfo> getConcertInfoList(long concertId) {
        List<ConcertInfo> getConcertInfoList = concertInfoRepository.getAllConcertInfoByConcertId(concertId);
        return getConcertInfoList.stream().map(concertInfo -> new ConcertInfoDomainDto.GetConcertInfoListInfo(
                concertInfo.getConcertInfoId(),
                concertInfo.getConcertId(),
                concertInfo.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ))).toList();
    }

}
