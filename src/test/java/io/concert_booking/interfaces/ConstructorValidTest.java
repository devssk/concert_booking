package io.concert_booking.interfaces;

import io.concert_booking.interfaces.account.AccountInterfaceDto;
import io.concert_booking.interfaces.concert.ConcertInterfaceDto;
import io.concert_booking.interfaces.exception.ValidException;
import io.concert_booking.interfaces.queue.QueueInterfaceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorValidTest {

    @Nested
    @DisplayName("AccountInterfaceDto 생성자 테스트")
    class AccountInterfaceDtoTest {

        @Test
        @DisplayName("ChargeRequest 생성자 테스트 - 에러테스트")
        void chargeRequestConstructorValidTest01() {
            // given
            Long accountIdIsNull = null;
            Long accountIdIsLessThanOne = 0L;
            Long accountId = 1L;

            Long amountIsNull = null;
            Long amountLessThanOne = 0L;
            Long amount = 1L;

            // when
            Throwable accountIdThrowable1 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.ChargeRequest(accountIdIsNull, amount));
            Throwable accountIdThrowable2 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.ChargeRequest(accountIdIsLessThanOne, amount));
            Throwable amountThrowable1 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.ChargeRequest(accountId, amountIsNull));
            Throwable amountThrowable2 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.ChargeRequest(accountId, amountLessThanOne));

            // then
            assertAll(() -> {
                assertEquals("accountId를 입력해 주세요.", accountIdThrowable1.getMessage());
                assertEquals("accountId의 올바른 범위를 입력해 주세요.", accountIdThrowable2.getMessage());
                assertEquals("amount를 입력해 주세요.", amountThrowable1.getMessage());
                assertEquals("amount의 올바른 범위를 입력해 주세요.", amountThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("ChargeRequest 생성자 테스트 - 정상")
        void chargeRequestConstructorValidTest02() {
            // given
            Long accountId = 1L;
            Long amount = 1000L;

            // when
            AccountInterfaceDto.ChargeRequest result = new AccountInterfaceDto.ChargeRequest(accountId, amount);

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(accountId, result.accountId());
                assertEquals(amount, result.amount());
            });
        }

        @Test
        @DisplayName("PaymentRequest 생성자 테스트 - 에러테스트")
        void paymentRequestConstructorValidTest01() {
            // given
            String tokenIsNull = null;
            String tokenIsEmpty = "";
            String token = "tempToken";

            Long concertSeatIdIsNull = null;
            Long concertSeatIdIsLessThanOne = 0L;
            Long concertSeatId = 1L;

            Long amountIsNull = null;
            Long amountLessThanOne = 0L;
            Long amount = 1L;

            // when
            Throwable tokenThrowable1 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.PaymentRequest(tokenIsNull, concertSeatId, amount));
            Throwable tokenThrowable2 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.PaymentRequest(tokenIsEmpty, concertSeatId, amount));
            Throwable concertSeatIdThrowable1 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.PaymentRequest(token, concertSeatIdIsNull, amount));
            Throwable concertSeatIdThrowable2 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.PaymentRequest(token, concertSeatIdIsLessThanOne, amount));
            Throwable amountThrowable1 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.PaymentRequest(token, concertSeatId, amountIsNull));
            Throwable amountThrowable2 = assertThrows(ValidException.class, () -> new AccountInterfaceDto.PaymentRequest(token, concertSeatId, amountLessThanOne));

            // then
            assertAll(() -> {
                assertEquals("token를 입력해 주세요.", tokenThrowable1.getMessage());
                assertEquals("token를 입력해 주세요.", tokenThrowable2.getMessage());
                assertEquals("concertSeatId를 입력해 주세요.", concertSeatIdThrowable1.getMessage());
                assertEquals("concertSeatId의 올바른 범위를 입력해 주세요.", concertSeatIdThrowable2.getMessage());
                assertEquals("amount를 입력해 주세요.", amountThrowable1.getMessage());
                assertEquals("amount의 올바른 범위를 입력해 주세요.", amountThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("PaymentRequest 생성자 테스트 - 정상")
        void paymentRequestConstructorValidTest02() {
            // given
            String token = "tempToken";
            Long concertSeatId = 1L;
            Long amount = 1L;

            // when
            AccountInterfaceDto.PaymentRequest result = new AccountInterfaceDto.PaymentRequest(token, concertSeatId, amount);

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(token, result.token());
                assertEquals(concertSeatId, result.concertSeatId());
                assertEquals(amount, result.amount());
            });
        }

    }

    @Nested
    @DisplayName("ConcertInterfaceDto 생성자 테스트")
    class ConcertInterfaceDtoTest {
        @Test
        @DisplayName("OccupancySeatRequest 생성자 테스트 - 에러테스트")
        void occupancySeatRequestConstructorValidTest01() {
            // given
            String tokenIsNull = null;
            String tokenIsEmpty = "";
            String token = "tempToken";

            Long concertSeatIdIsNull = null;
            Long concertSeatIdIsLessThanOne = 0L;
            Long concertSeatId = 1L;

            // when
            Throwable tokenThrowable1 = assertThrows(ValidException.class, () -> new ConcertInterfaceDto.OccupancySeatRequest(tokenIsNull, concertSeatId));
            Throwable tokenThrowable2 = assertThrows(ValidException.class, () -> new ConcertInterfaceDto.OccupancySeatRequest(tokenIsEmpty, concertSeatId));
            Throwable concertSeatIdThrowable1 = assertThrows(ValidException.class, () -> new ConcertInterfaceDto.OccupancySeatRequest(token, concertSeatIdIsNull));
            Throwable concertSeatIdThrowable2 = assertThrows(ValidException.class, () -> new ConcertInterfaceDto.OccupancySeatRequest(token, concertSeatIdIsLessThanOne));

            // then
            assertAll(() -> {
                assertEquals("token을 입력해 주세요.", tokenThrowable1.getMessage());
                assertEquals("token을 입력해 주세요.", tokenThrowable2.getMessage());
                assertEquals("concertSeatId를 입력해 주세요.", concertSeatIdThrowable1.getMessage());
                assertEquals("concertSeatId의 올바른 범위를 입력해 주세요.", concertSeatIdThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("OccupancySeatRequest 생성자 테스트 - 정상")
        void occupancySeatRequestConstructorValidTest02() {
            // given
            String token = "tempToken";
            Long concertSeatId = 1L;

            // when
            ConcertInterfaceDto.OccupancySeatRequest result = new ConcertInterfaceDto.OccupancySeatRequest(token, concertSeatId);

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(token, result.token());
                assertEquals(concertSeatId, result.concertSeatId());
            });
        }

    }

    @Nested
    @DisplayName("QueueInterfaceDto 생성자 테스트")
    class QueueInterfaceDtoTest {

        @Test
        @DisplayName("IssueTokenRequest 생성자 테스트 - 에러테스트")
        void issueTokenRequestConstructorValidTest01() {
            // given
            Long userIdIsNull = null;
            Long userIdIsLessThanOne = 0L;
            Long userId = 1L;

            Long concertInfoIdIsNull = null;
            Long concertInfoIdLessThanOne = 0L;
            Long concertInfoId = 1L;

            // when
            Throwable userIdThrowable1 = assertThrows(ValidException.class, () -> new QueueInterfaceDto.IssueTokenRequest(userIdIsNull, concertInfoId));
            Throwable userIdThrowable2 = assertThrows(ValidException.class, () -> new QueueInterfaceDto.IssueTokenRequest(userIdIsLessThanOne, concertInfoId));
            Throwable concertInfoIdThrowable1 = assertThrows(ValidException.class, () -> new QueueInterfaceDto.IssueTokenRequest(userId, concertInfoIdIsNull));
            Throwable concertInfoIdThrowable2 = assertThrows(ValidException.class, () -> new QueueInterfaceDto.IssueTokenRequest(userId, concertInfoIdLessThanOne));

            // then
            assertAll(() -> {
                assertEquals("userId를 입력해 주세요.", userIdThrowable1.getMessage());
                assertEquals("userId의 올바른 범위를 입력해 주세요.", userIdThrowable2.getMessage());
                assertEquals("concertInfoId를 입력해 주세요.", concertInfoIdThrowable1.getMessage());
                assertEquals("concertInfoId의 올바른 범위를 입력해 주세요.", concertInfoIdThrowable2.getMessage());
            });
        }

        @Test
        @DisplayName("IssueTokenRequest 생성자 테스트 - 정상")
        void issueTokenRequestConstructorValidTest02() {
            // given
            Long userId = 1L;
            Long concertInfoId = 1L;

            // when
            QueueInterfaceDto.IssueTokenRequest result = new QueueInterfaceDto.IssueTokenRequest(userId, concertInfoId);

            // then
            assertAll(() -> {
                assertNotNull(result);
                assertEquals(userId, result.userId());
                assertEquals(concertInfoId, result.concertInfoId());
            });
        }

    }


}