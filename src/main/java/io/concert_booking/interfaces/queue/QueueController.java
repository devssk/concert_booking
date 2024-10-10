package io.concert_booking.interfaces.queue;

import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queues")
public class QueueController {

    @PostMapping("/token")
    public ResponseDto issueToken(@RequestBody QueueDto.IssueTokenRequest request) {
        return new ResponseDto(ResponseCode.SUCC, new QueueDto.IssueTokenResponse("tempToken"));
    }

    @GetMapping("/waiting_number")
    public ResponseDto getWaitingNumber() {
        return new ResponseDto(ResponseCode.SUCC, new QueueDto.WaitingNumberResponse(5042));
    }

}
