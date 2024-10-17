package io.concert_booking.interfaces.account;

import io.concert_booking.application.account.AccountFacade;
import io.concert_booking.application.account.dto.AccountFacadeDto;
import io.concert_booking.domain.account.dto.AccountDomainDto;
import io.concert_booking.domain.account.service.AccountService;
import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import io.concert_booking.interfaces.exception.ValidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountFacade accountFacade;
    private final AccountService accountService;

    @Operation(summary = "계좌 충전", description = "계좌 잔액 충전 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountInterfaceDto.ChargeResponse.class)))
    @PatchMapping("/charge")
    public ResponseDto charge(@RequestBody AccountInterfaceDto.ChargeRequest request) {
        AccountDomainDto.ChargeAccountInfo result = accountService.chargeAccount(new AccountDomainDto.ChargeAccountCommand(request.accountId(), request.amount()));
        return new ResponseDto(ResponseCode.SUCC, new AccountInterfaceDto.ChargeResponse(result.accountId(), result.amount(), result.amount()));
    }

    @Operation(summary = "결제", description = "콘서트 결제 기능, 좌석 예약 완료까지 같이 되는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountInterfaceDto.PaymentResponse.class)))
    @PatchMapping("/payment")
    public ResponseDto payment(@RequestBody AccountInterfaceDto.PaymentRequest request) {
        AccountFacadeDto.PaymentConcertResult result = accountFacade.paymentConcert(new AccountFacadeDto.PaymentConcertCriteria(request.token(), request.concertSeatId(), request.amount()));
        return new ResponseDto(ResponseCode.SUCC, new AccountInterfaceDto.PaymentResponse(
                result.concertName(),
                result.concertDate(),
                result.concertSeatNumber(),
                result.paymentAmount()
        ));
    }

    @Operation(summary = "잔액 확인", description = "계좌 잔액 확인하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountInterfaceDto.AccountBalanceResponse.class)))
    @GetMapping("/balance")
    public ResponseDto getAccountBalance(@RequestParam(name = "accountId") Long accountId) {
        if (accountId == null || accountId < 0) {
            String message = accountId == null ? "accountId를 입력해 주세요." : "accountId의 올바른 범위를 입력해 주세요.";
            throw new ValidException(message);
        }
        AccountDomainDto.GetAccountByIdInfo getAccountInfo = accountService.getAccountById(accountId);
        return new ResponseDto(ResponseCode.SUCC, new AccountInterfaceDto.AccountBalanceResponse(getAccountInfo.balance()));
    }

}
