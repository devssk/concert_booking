package io.concert_booking.interfaces.queue;

import io.concert_booking.application.queue.QueueFacade;
import io.concert_booking.application.queue.dto.QueueFacadeDto;
import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
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
        request.validate();
        QueueFacadeDto.RegisterQueueResult result = queueFacade.registerQueue(new QueueFacadeDto.RegisterQueueCriteria(
                request.memberId(),
                request.concertInfoId()
        ));
        return new ResponseDto(ResponseCode.SUCC, new QueueInterfaceDto.IssueTokenResponse(result.token()));
    }

    @Operation(summary = "내 대기열 조회", description = "내 대기열을 조회하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QueueInterfaceDto.WaitingNumberResponse.class)))
    @GetMapping("/waiting_number")
    public ResponseDto getWaitingNumber(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        QueueFacadeDto.GetMyQueueNumberResult result = queueFacade.getMyQueueNumber(token);
        return new ResponseDto(ResponseCode.SUCC, new QueueInterfaceDto.WaitingNumberResponse(
                result.frontQueue(),
                result.myQueueNumber(),
                result.backQueue(),
                result.queueStatus()
        ));
    }

}
