package io.concert_booking.domain.concert.service;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.concert.dto.ConcertSeatDomainDto;
import io.concert_booking.domain.concert.entity.ConcertSeat;
import io.concert_booking.domain.concert.entity.SeatStatus;
import io.concert_booking.domain.concert.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertSeatService {

    private final ConcertSeatRepository concertSeatRepository;

    @Transactional
    public ConcertSeatDomainDto.RegisterConcertSeatInfo registerConcertSeat(ConcertSeatDomainDto.RegisterConcertSeatCommand command) {
        ConcertSeat concertSeat = new ConcertSeat(command.concertInfoId(), command.seatNumber(), SeatStatus.OPEN);
        ConcertSeat saveConcertSeat = concertSeatRepository.save(concertSeat);
        return new ConcertSeatDomainDto.RegisterConcertSeatInfo(saveConcertSeat.getConcertSeatId());
    }

    @Transactional
    public ConcertSeatDomainDto.UpdateConcertSeatStatusInfo updateConcertSeatStatus(ConcertSeatDomainDto.UpdateConcertSeatStatusCommand command) {
        ConcertSeat getConcertSeat = concertSeatRepository.getConcertSeatById(command.concertSeatId());
        getConcertSeat.updateSeatStatus(command.seatStatus());
        return new ConcertSeatDomainDto.UpdateConcertSeatStatusInfo(getConcertSeat.getConcertSeatId());
    }

    @Transactional
    public ConcertSeatDomainDto.OccupancySeatInfo occupancySeat(ConcertSeatDomainDto.OccupancySeatCommand command) {
        ConcertSeat getConcertSeat;
        try {
            getConcertSeat = concertSeatRepository.getConcertSeatById(command.concertSeatId());
            getConcertSeat.occupancySeat(command.memberId());
        } catch (Exception e) {
            throw new ConcertBookingException(ErrorCode.OCCUPANCY_SEAT);
        }
        return new ConcertSeatDomainDto.OccupancySeatInfo(getConcertSeat.getConcertSeatId(), getConcertSeat.getMemberId(), getConcertSeat.getSeatStatus().name());
    }

    @Transactional(readOnly = true)
    public ConcertSeatDomainDto.GetConcertSeatInfo getConcertSeat(long concertSeatId) {
        ConcertSeat getConcertSeat = concertSeatRepository.getConcertSeatById(concertSeatId);
        return new ConcertSeatDomainDto.GetConcertSeatInfo(
                getConcertSeat.getConcertSeatId(),
                getConcertSeat.getConcertInfoId(),
                getConcertSeat.getSeatNumber(),
                getConcertSeat.getSeatStatus().name(),
                getConcertSeat.getCreatedAt(),
                getConcertSeat.getUpdatedAt()
                );
    }

    @Transactional(readOnly = true)
    public List<ConcertSeatDomainDto.GetConcertSeatListInfo> getConcertSeatList(long concertInfoId) {
        List<ConcertSeat> getConcertSeatList = concertSeatRepository.getAllConcertSeatByConcertInfoId(concertInfoId);
        return getConcertSeatList.stream().map(concertSeat -> new ConcertSeatDomainDto.GetConcertSeatListInfo(
                concertSeat.getConcertSeatId(),
                concertSeat.getConcertInfoId(),
                concertSeat.getSeatNumber(),
                concertSeat.getSeatStatus().name(),
                concertSeat.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                concertSeat.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )).toList();
    }

    @Transactional
    public void occupancySeatCheckAndClean() {
        concertSeatRepository.updateConcertSeatStatusOccupancyExpired();
    }

}
