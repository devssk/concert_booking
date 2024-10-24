package io.concert_booking.application.concert;

import io.concert_booking.application.concert.dto.ConcertFacadeDto;
import io.concert_booking.domain.concert.dto.ConcertDomainDto;
import io.concert_booking.domain.concert.dto.ConcertInfoDomainDto;
import io.concert_booking.domain.concert.dto.ConcertSeatDomainDto;
import io.concert_booking.domain.concert.entity.SeatStatus;
import io.concert_booking.domain.concert.service.ConcertInfoService;
import io.concert_booking.domain.concert.service.ConcertSeatService;
import io.concert_booking.domain.concert.service.ConcertService;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;
    private final ConcertInfoService concertInfoService;
    private final ConcertSeatService concertSeatService;
    private final QueueService queueService;
    private final TokenService tokenService;

    public List<ConcertFacadeDto.GetConcertInfoResult> getConcertInfo(long concertId) {
        ConcertDomainDto.GetConcertInfo getConcert = concertService.getConcert(concertId);
        List<ConcertInfoDomainDto.GetConcertInfoListInfo> concertInfoList = concertInfoService.getConcertInfoList(getConcert.concertId());
        return concertInfoList.stream().map(concertInfo -> new ConcertFacadeDto.GetConcertInfoResult(
                getConcert.concertId(),
                concertInfo.concertInfoId(),
                getConcert.concertName(),
                concertInfo.concertDate()
        )).toList();
    }

    public List<ConcertFacadeDto.GetConcertSeatListResult> getConcertSeatList(String token) {
        Map<String, Long> payload;
        try {
            payload = tokenService.decodeToken(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        long queueId = payload.get("queueId");
        long concertId = payload.get("concertId");
        long concertInfoId = payload.get("concertInfoId");

        QueueDomainDto.GetQueueInfo getQueueInfo = queueService.getQueue(queueId);
        if (!getQueueInfo.queueStatus().equals(QueueStatus.ENTER.name())) {
            throw new IllegalArgumentException("대기열에 통과된 토큰이 아닙니다.");
        }
        concertService.getConcert(concertId);
        concertInfoService.getConcertInfo(concertInfoId);
        List<ConcertSeatDomainDto.GetConcertSeatListInfo> concertSeatInfoList = concertSeatService.getConcertSeatList(concertInfoId);

        return concertSeatInfoList.stream().map(concertSeatInfo -> new ConcertFacadeDto.GetConcertSeatListResult(
                concertSeatInfo.concertSeatId(),
                concertSeatInfo.concertInfoId(),
                concertSeatInfo.seatNumber(),
                concertSeatInfo.seatStatus(),
                concertSeatInfo.createdAt(),
                concertSeatInfo.updatedAt()
        )).toList();
    }

    @Transactional
    public ConcertFacadeDto.OccupancyConcertSeatResult OccupancyConcertSeat(ConcertFacadeDto.OccupancyConcertSeatCriteria criteria) {
        Map<String, Long> payload;
        try {
            payload = tokenService.decodeToken(criteria.token());
        } catch (Exception e) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        long queueId = payload.get("queueId");
        long memberId = payload.get("memberId");
        long concertId = payload.get("concertId");
        long concertInfoId = payload.get("concertInfoId");
        long concertSeatId = criteria.concertSeatId();

        QueueDomainDto.GetQueueInfo getQueueInfo = queueService.getQueue(queueId);
        if (!getQueueInfo.queueStatus().equals(QueueStatus.ENTER.name())) {
            throw new IllegalArgumentException("대기열에 통과된 토큰이 아닙니다.");
        }

        concertService.getConcert(concertId);
        concertInfoService.getConcertInfo(concertInfoId);

        ConcertSeatDomainDto.GetConcertSeatInfo concertSeat = concertSeatService.getConcertSeat(concertSeatId);
        if (!concertSeat.seatStatus().equals(SeatStatus.OPEN.name())) {
            throw new IllegalArgumentException("이미 결제 대기중인 좌석입니다.");
        }
        ConcertSeatDomainDto.OccupancySeatInfo getConcertSeatInfo = concertSeatService.occupancySeat(new ConcertSeatDomainDto.OccupancySeatCommand(concertSeatId, memberId));

        queueService.updateQueueStatus(new QueueDomainDto.UpdateQueueStatusCommand(queueId, QueueStatus.OCCUPANCY));

        return new ConcertFacadeDto.OccupancyConcertSeatResult(getConcertSeatInfo.concertSeatId(), getConcertSeatInfo.memberId(), getConcertSeatInfo.seatStatus());
    }

}