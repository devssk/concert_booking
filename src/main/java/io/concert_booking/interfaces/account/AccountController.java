package io.concert_booking.interfaces.account;

import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @PatchMapping("/charge")
    public ResponseDto charge(@RequestBody AccountDto.ChargeRequest request) {
        return new ResponseDto(ResponseCode.SUCC, new AccountDto.ChargeResponse(30000L));
    }

    @PatchMapping("/payment")
    public ResponseDto payment(@RequestBody AccountDto.PaymentRequest request) {
        return new ResponseDto(ResponseCode.SUCC, new AccountDto.PaymentResponse("2024 IU CONCERT HER", "2024-03-03", 1, 169000L));
    }

    @GetMapping("/{memberId}/balance")
    public ResponseDto getAccountBalance(@PathVariable(name = "memberId") Long memberId) {
        return new ResponseDto(ResponseCode.SUCC, new AccountDto.AccountBalanceResponse(30000L));
    }

}
