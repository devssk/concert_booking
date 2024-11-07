package io.concert_booking.interfaces;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.interfaces.account.AccountInterfaceDto;
import io.concert_booking.interfaces.concert.ConcertInterfaceDto;
import io.concert_booking.interfaces.queue.QueueInterfaceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestDtoValidTest {

    @Nested
    @DisplayName("AccountInterfaceDto 유효성 테스트")
    class AccountInterfaceDtoTest {

        @Test
        @DisplayName("ChargeRequest 유효성 테스트 - 에러테스트")
        void chargeRequestConstructorValidTest01() {
            // given
            Long accountIdIsNull = null;
            Long accountIdIsLessThanOne = 0L;
            Long accountId = 1L;

            Long amountIsNull = null;
            Long amountLessThanOne = 0L;
            Long amount = 1L;

            // when
            AccountInterfaceDto.ChargeRequest chargeRequest1 = new AccountInterfaceDto.ChargeRequest(accountIdIsNull, amount);
            AccountInterfaceDto.ChargeRequest chargeRequest2 = new AccountInterfaceDto.ChargeRequest(accountIdIsLessThanOne, amount);
            AccountInterfaceDto.ChargeRequest chargeRequest3 = new AccountInterfaceDto.ChargeRequest(accountId, amountIsNull);
            AccountInterfaceDto.ChargeRequest chargeRequest4 = new AccountInterfaceDto.ChargeRequest(accountId, amountLessThanOne);
            Throwable accountIdThrowable1 = assertThrows(ConcertBookingException.class, chargeRequest1::validate);
            Throwable accountIdThrowable2 = assertThrows(ConcertBookingException.class, chargeRequest2::validate);
            Throwable amountThrowable1 = assertThrows(ConcertBookingException.class, chargeRequest3::validate);
            Throwable amountThrowable2 = assertThrows(ConcertBookingException.class, chargeRequest4::validate);

            // then
            assertAll(() -> {
                assertEquals("유효성 검사 실패, accountId", accountIdThrowable1.getMessage());
                assertEquals("유효성 검사 실패, accountId", accountIdThrowable2.getMessage());
                assertEquals("유효성 검사 실패, amount", amountThrowable1.getMessage());
                assertEquals("유효성 검사 실패, amount", amountThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("ChargeRequest 유효성 테스트 - 정상")
        void chargeRequestConstructorValidTest02() {
            // given
            Long accountId = 1L;
            Long amount = 1000L;

            // when
            AccountInterfaceDto.ChargeRequest result = new AccountInterfaceDto.ChargeRequest(accountId, amount);
            result.validate();

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(accountId, result.accountId());
                assertEquals(amount, result.amount());
            });
        }

        @Test
        @DisplayName("PaymentRequest 유효성 테스트 - 에러테스트")
        void paymentRequestConstructorValidTest01() {
            // given
            Long concertSeatIdIsNull = null;
            Long concertSeatIdIsLessThanOne = 0L;
            Long concertSeatId = 1L;

            Long amountIsNull = null;
            Long amountLessThanOne = 0L;
            Long amount = 1L;

            // when
            AccountInterfaceDto.PaymentRequest paymentRequest3 = new AccountInterfaceDto.PaymentRequest(concertSeatIdIsNull, amount);
            AccountInterfaceDto.PaymentRequest paymentRequest4 = new AccountInterfaceDto.PaymentRequest(concertSeatIdIsLessThanOne, amount);
            AccountInterfaceDto.PaymentRequest paymentRequest5 = new AccountInterfaceDto.PaymentRequest(concertSeatId, amountIsNull);
            AccountInterfaceDto.PaymentRequest paymentRequest6 = new AccountInterfaceDto.PaymentRequest(concertSeatId, amountLessThanOne);
            Throwable concertSeatIdThrowable1 = assertThrows(ConcertBookingException.class, paymentRequest3::validate);
            Throwable concertSeatIdThrowable2 = assertThrows(ConcertBookingException.class, paymentRequest4::validate);
            Throwable amountThrowable1 = assertThrows(ConcertBookingException.class, paymentRequest5::validate);
            Throwable amountThrowable2 = assertThrows(ConcertBookingException.class, paymentRequest6::validate);

            // then
            assertAll(() -> {
                assertEquals("유효성 검사 실패, concertSeatId", concertSeatIdThrowable1.getMessage());
                assertEquals("유효성 검사 실패, concertSeatId", concertSeatIdThrowable2.getMessage());
                assertEquals("유효성 검사 실패, amount", amountThrowable1.getMessage());
                assertEquals("유효성 검사 실패, amount", amountThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("PaymentRequest 유효성 테스트 - 정상")
        void paymentRequestConstructorValidTest02() {
            // given
            String token = "tempToken";
            Long concertSeatId = 1L;
            Long amount = 1L;

            // when
            AccountInterfaceDto.PaymentRequest result = new AccountInterfaceDto.PaymentRequest(concertSeatId, amount);
            result.validate();

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(concertSeatId, result.concertSeatId());
                assertEquals(amount, result.amount());
            });
        }

    }

    @Nested
    @DisplayName("ConcertInterfaceDto 유효성 테스트")
    class ConcertInterfaceDtoTest {
        @Test
        @DisplayName("OccupancySeatRequest 유효성 테스트 - 에러테스트")
        void occupancySeatRequestConstructorValidTest01() {
            // given
            Long concertSeatIdIsNull = null;
            Long concertSeatIdIsLessThanOne = 0L;

            // when
            ConcertInterfaceDto.OccupancySeatRequest occupancySeatRequest1 = new ConcertInterfaceDto.OccupancySeatRequest(concertSeatIdIsNull);
            ConcertInterfaceDto.OccupancySeatRequest occupancySeatRequest2 = new ConcertInterfaceDto.OccupancySeatRequest(concertSeatIdIsLessThanOne);
            Throwable concertSeatIdThrowable1 = assertThrows(ConcertBookingException.class, occupancySeatRequest1::validate);
            Throwable concertSeatIdThrowable2 = assertThrows(ConcertBookingException.class, occupancySeatRequest2::validate);

            // then
            assertAll(() -> {
                assertEquals("유효성 검사 실패, concertSeatId", concertSeatIdThrowable1.getMessage());
                assertEquals("유효성 검사 실패, concertSeatId", concertSeatIdThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("OccupancySeatRequest 생성자 테스트 - 정상")
        void occupancySeatRequestConstructorValidTest02() {
            // given
            Long concertSeatId = 1L;

            // when
            ConcertInterfaceDto.OccupancySeatRequest result = new ConcertInterfaceDto.OccupancySeatRequest(concertSeatId);
            result.validate();

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(concertSeatId, result.concertSeatId());
            });
        }

    }

    @Nested
    @DisplayName("QueueInterfaceDto 유효성 테스트")
    class QueueInterfaceDtoTest {

        @Test
        @DisplayName("IssueTokenRequest 유효성 테스트 - 에러테스트")
        void issueTokenRequestConstructorValidTest01() {
            // given
            Long memberIdIsNull = null;
            Long memberIdIsLessThanOne = 0L;
            Long memberId = 1L;

            Long concertInfoIdIsNull = null;
            Long concertInfoIdLessThanOne = 0L;
            Long concertInfoId = 1L;

            // when
            QueueInterfaceDto.IssueTokenRequest issueTokenRequest1 = new QueueInterfaceDto.IssueTokenRequest(memberIdIsNull, concertInfoId);
            QueueInterfaceDto.IssueTokenRequest issueTokenRequest2 = new QueueInterfaceDto.IssueTokenRequest(memberIdIsLessThanOne, concertInfoId);
            QueueInterfaceDto.IssueTokenRequest issueTokenRequest3 = new QueueInterfaceDto.IssueTokenRequest(memberId, concertInfoIdIsNull);
            QueueInterfaceDto.IssueTokenRequest issueTokenRequest4 = new QueueInterfaceDto.IssueTokenRequest(memberId, concertInfoIdLessThanOne);
            Throwable userIdThrowable1 = assertThrows(ConcertBookingException.class, issueTokenRequest1::validate);
            Throwable userIdThrowable2 = assertThrows(ConcertBookingException.class, issueTokenRequest2::validate);
            Throwable concertInfoIdThrowable1 = assertThrows(ConcertBookingException.class, issueTokenRequest3::validate);
            Throwable concertInfoIdThrowable2 = assertThrows(ConcertBookingException.class, issueTokenRequest4::validate);

            // then
            assertAll(() -> {
                assertEquals("유효성 검사 실패, memberId", userIdThrowable1.getMessage());
                assertEquals("유효성 검사 실패, memberId", userIdThrowable2.getMessage());
                assertEquals("유효성 검사 실패, concertInfoId", concertInfoIdThrowable1.getMessage());
                assertEquals("유효성 검사 실패, concertInfoId", concertInfoIdThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("IssueTokenRequest 유효성 테스트 - 정상")
        void issueTokenRequestConstructorValidTest02() {
            // given
            Long memberId = 1L;
            Long concertInfoId = 1L;

            // when
            QueueInterfaceDto.IssueTokenRequest result = new QueueInterfaceDto.IssueTokenRequest(memberId, concertInfoId);
            result.validate();

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(memberId, result.memberId());
                assertEquals(concertInfoId, result.concertInfoId());
            });
        }

    }


}