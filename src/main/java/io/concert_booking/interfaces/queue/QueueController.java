package io.concert_booking.interfaces.queue;

import io.concert_booking.application.queue.QueueFacade;
import io.concert_booking.application.queue.dto.QueueFacadeDto;
import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import io.concert_booking.interfaces.concert.ConcertInterfaceDto;
import io.concert_booking.interfaces.exception.ValidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queues")
public class QueueController {

    private final QueueFacade queueFacade;

    @Operation(summary = "대기열 등록", description = "대기열 등록 및 토큰 발급 하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QueueInterfaceDto.IssueTokenResponse.class)))
    @PostMapping("/token")
    public ResponseDto issueToken(@RequestBody QueueInterfaceDto.IssueTokenRequest request) {
        QueueFacadeDto.RegisterQueueResult result = queueFacade.registerQueue(new QueueFacadeDto.RegisterQueueCriteria(
                request.userId(),
                request.concertInfoId()
        ));
        return new ResponseDto(ResponseCode.SUCC, new QueueInterfaceDto.IssueTokenResponse(result.token()));
    }

    @Operation(summary = "내 대기열 조회", description = "내 대기열을 조회하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QueueInterfaceDto.WaitingNumberResponse.class)))
    @GetMapping("/waiting_number")
    public ResponseDto getWaitingNumber(@RequestParam(name = "token") String token) {
        if (token == null || token.isEmpty()) {
            throw new ValidException("token을 입력해 주세요.");
        }
        QueueFacadeDto.GetMyQueueNumberResult result = queueFacade.getMyQueueNumber(token);
        return new ResponseDto(ResponseCode.SUCC, new QueueInterfaceDto.WaitingNumberResponse(
                result.frontQueue(),
                result.myQueueNumber(),
                result.backQueue(),
                result.queueStatus()
        ));
    }

}
