package io.concert_booking.application;

import io.concert_booking.application.account.AccountFacade;
import io.concert_booking.application.account.dto.AccountFacadeDto;
import io.concert_booking.common.exception.ConcertBookingException;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountFacadeTest {

    @InjectMocks
    AccountFacade accountFacade;

    @Mock
    AccountService accountService;
    @Mock
    MemberService memberService;
    @Mock
    PerformerService performerService;
    @Mock
    ConcertService concertService;
    @Mock
    ConcertInfoService concertInfoService;
    @Mock
    ConcertSeatService concertSeatService;
    @Mock
    BookingService bookingService;
    @Mock
    QueueService queueService;
    @Mock
    TokenService tokenService;

    @Test
    @DisplayName("결제하기 - 정상")
    void paymentConcertTest01() throws Exception {
        // given
        String token = "token";
        long queueId = 1L;
        long memberId = 1L;
        String memberName = "유애나";
        long performerId = 1L;
        String perfomerName = "아이유";
        long concertId = 1L;
        String concertName = "2024 IU CONCERT HER";
        long concertInfoId = 1L;
        String concertDate = "2024-12-25";
        long concertSeatId = 1L;
        int seatNumber = 1;
        long accountId = 1L;
        long balance = 200000L;
        long amount = 169000L;
        long accountHistoryId = 1L;
        long bookingId = 1L;
        LocalDateTime createdAtLocalDateTime = LocalDateTime.now().minusMinutes(3);
        String createdAt = createdAtLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Long> payload = new HashMap<>();
        payload.put("queueId", queueId);
        payload.put("memberId", memberId);
        payload.put("concertId", concertId);
        payload.put("concertInfoId", concertInfoId);

        QueueDomainDto.GetQueueInfo getQueueInfo = new QueueDomainDto.GetQueueInfo(queueId, concertId, QueueStatus.OCCUPANCY.name(), createdAt);
        MemberDomainDto.GetMemberInfo getMemberInfo = new MemberDomainDto.GetMemberInfo(memberId, memberName);
        ConcertDomainDto.GetConcertInfo getConcertInfo = new ConcertDomainDto.GetConcertInfo(concertId, performerId, concertName);
        PerformerDomainDto.GetPerformerInfo getPerformerInfo = new PerformerDomainDto.GetPerformerInfo(performerId, perfomerName);
        ConcertInfoDomainDto.GetConcertInfoInfo getConcertInfoInfo = new ConcertInfoDomainDto.GetConcertInfoInfo(concertInfoId, concertId, concertDate);
        ConcertSeatDomainDto.GetConcertSeatInfo getConcertSeatInfo = new ConcertSeatDomainDto.GetConcertSeatInfo(concertSeatId, concertInfoId, seatNumber, SeatStatus.OCCUPANCY.name(), createdAtLocalDateTime, createdAtLocalDateTime);
        AccountDomainDto.GetAccountByMemberIdInfo getAccountInfo = new AccountDomainDto.GetAccountByMemberIdInfo(accountId, balance);
        AccountDomainDto.PaymentAccountInfo paymentAccountInfo = new AccountDomainDto.PaymentAccountInfo(accountId, accountHistoryId, amount, balance - amount);
        BookingDomainDto.RegisterBookingInfo registerBookingInfo = new BookingDomainDto.RegisterBookingInfo(bookingId);

        AccountFacadeDto.PaymentConcertCriteria criteria = new AccountFacadeDto.PaymentConcertCriteria(token, concertSeatId, amount);

        doReturn(payload).when(tokenService).decodeToken(token);
        doReturn(getConcertSeatInfo).when(concertSeatService).getConcertSeat(anyLong());
        doReturn(getMemberInfo).when(memberService).getMember(anyLong());
        doReturn(getConcertInfo).when(concertService).getConcert(anyLong());
        doReturn(getPerformerInfo).when(performerService).getPerformer(anyLong());
        doReturn(getConcertInfoInfo).when(concertInfoService).getConcertInfo(anyLong());
        doReturn(getAccountInfo).when(accountService).getAccountByMemberId(anyLong());
        doReturn(paymentAccountInfo).when(accountService).paymentAccount(any());
        doReturn(registerBookingInfo).when(bookingService).registerBooking(any());

        // when
        AccountFacadeDto.PaymentConcertResult result = accountFacade.paymentConcert(criteria);

        // then
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(bookingId, result.bookingId());
            assertEquals(concertName, result.concertName());
            assertEquals(concertDate, result.concertDate());
            assertEquals(seatNumber, result.concertSeatNumber());
            assertEquals(amount, result.paymentAmount());
        });
    }

    @Test
    @DisplayName("결제하기 - 좌석 점유 시간 초과")
    void paymentConcertTest04() throws Exception {
        // given
        String token = "token";
        long queueId = 1L;
        long memberId = 1L;
        long concertId = 1L;
        long concertInfoId = 1L;
        long concertSeatId = 1L;
        int seatNumber = 1;
        long amount = 169000L;
        LocalDateTime createdAtLocalDateTime = LocalDateTime.now().minusMinutes(6);
        String createdAt = createdAtLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Long> payload = new HashMap<>();
        payload.put("queueId", queueId);
        payload.put("memberId", memberId);
        payload.put("concertId", concertId);
        payload.put("concertInfoId", concertInfoId);

        ConcertSeatDomainDto.GetConcertSeatInfo getConcertSeatInfo = new ConcertSeatDomainDto.GetConcertSeatInfo(concertSeatId, concertInfoId, seatNumber, SeatStatus.OCCUPANCY.name(), createdAtLocalDateTime, createdAtLocalDateTime);

        AccountFacadeDto.PaymentConcertCriteria criteria = new AccountFacadeDto.PaymentConcertCriteria(token, concertSeatId, amount);

        doReturn(payload).when(tokenService).decodeToken(token);
        doReturn(getConcertSeatInfo).when(concertSeatService).getConcertSeat(anyLong());

        // when
        Throwable throwable = assertThrows(ConcertBookingException.class, () -> accountFacade.paymentConcert(criteria));

        // then
        assertEquals("좌석 점유 시간 만료", throwable.getMessage());
    }

}