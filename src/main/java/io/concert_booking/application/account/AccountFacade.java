package io.concert_booking.application.account;

import io.concert_booking.application.account.dto.AccountFacadeDto;
import io.concert_booking.domain.account.dto.AccountDomainDto;
import io.concert_booking.domain.account.service.AccountService;
import io.concert_booking.domain.concert.dto.*;
import io.concert_booking.domain.concert.entity.SeatStatus;
import io.concert_booking.domain.concert.service.*;
import io.concert_booking.domain.member.dto.MemberDomainDto;
import io.concert_booking.domain.member.service.MemberService;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;
    private final MemberService memberService;
    private final PerformerService performerService;
    private final ConcertService concertService;
    private final ConcertInfoService concertInfoService;
    private final ConcertSeatService concertSeatService;
    private final BookingService bookingService;
    private final QueueService queueService;
    private final TokenService tokenService;


    public AccountFacadeDto.PaymentConcertResult paymentConcert(AccountFacadeDto.PaymentConcertCriteria criteria) {
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
        if (!getQueueInfo.queueStatus().equals(QueueStatus.OCCUPANCY.name())) {
            throw new IllegalArgumentException("좌석을 점유한 토큰이 아닙니다.");
        }

        ConcertSeatDomainDto.GetConcertSeatInfo getConcertSeatInfo = concertSeatService.getConcertSeat(concertSeatId);
        Duration duration = Duration.between(getConcertSeatInfo.updatedAt(), LocalDateTime.now());
        if (duration.toSeconds() > 300) {
            throw new IllegalArgumentException("좌석 임시 점유 시간이 종료되었습니다.");
        }

        MemberDomainDto.GetMemberInfo getMemberInfo = memberService.getMember(memberId);
        ConcertDomainDto.GetConcertInfo getConcertInfo = concertService.getConcert(concertId);
        PerformerDomainDto.GetPerformerInfo getPerformerInfo = performerService.getPerformer(getConcertInfo.performerId());
        ConcertInfoDomainDto.GetConcertInfoInfo getConcertInfoInfo = concertInfoService.getConcertInfo(concertInfoId);


        AccountDomainDto.GetAccountByMemberIdInfo getAccountInfo = accountService.getAccountByMemberId(memberId);

        AccountDomainDto.PaymentAccountInfo paymentAccountInfo = accountService.paymentAccount(new AccountDomainDto.PaymentAccountCommand(getAccountInfo.accountId(), criteria.amount()));

        concertSeatService.updateConcertSeatStatus(new ConcertSeatDomainDto.UpdateConcertSeatStatusCommand(concertSeatId, SeatStatus.PAYMENT));

        BookingDomainDto.RegisterBookingInfo registerBookingInfo = bookingService.registerBooking(new BookingDomainDto.RegisterBookingCommand(
                getConcertInfo.performerId(),
                getConcertInfo.concertId(),
                getConcertInfoInfo.concertInfoId(),
                getConcertSeatInfo.concertSeatId(),
                getMemberInfo.memberId(),
                paymentAccountInfo.accountHistoryId(),
                getPerformerInfo.performerName(),
                getConcertInfo.concertName(),
                getConcertInfoInfo.concertDate(),
                getConcertSeatInfo.seatNumber(),
                paymentAccountInfo.amount(),
                getMemberInfo.memberName()
        ));

        queueService.deleteQueue(queueId);

        return new AccountFacadeDto.PaymentConcertResult(
                registerBookingInfo.bookingId(),
                getConcertInfo.concertName(),
                getConcertInfoInfo.concertDate(),
                getConcertSeatInfo.seatNumber(),
                paymentAccountInfo.amount());
    }

}