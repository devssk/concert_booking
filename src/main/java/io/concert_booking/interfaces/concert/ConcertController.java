package io.concert_booking.interfaces.concert;

import io.concert_booking.application.concert.ConcertFacade;
import io.concert_booking.application.concert.dto.ConcertFacadeDto;
import io.concert_booking.domain.concert.dto.BookingDomainDto;
import io.concert_booking.domain.concert.service.BookingService;
import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import io.concert_booking.interfaces.account.AccountInterfaceDto;
import io.concert_booking.interfaces.exception.ValidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertFacade concertFacade;
    private final BookingService bookingService;

    @Operation(summary = "콘서트 정보 조회", description = "콘서트 정보를 조회하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertInterfaceDto.ConcertInfoResponse.class)))
    @GetMapping("/info")
    public ResponseDto getConcertInfo(@RequestParam(name = "concertId") Long concertId) {
        List<ConcertFacadeDto.GetConcertInfoResult> getResult = concertFacade.getConcertInfo(concertId);
        List<ConcertInterfaceDto.ConcertInfoResponse> convert = getResult.stream().map(result -> new ConcertInterfaceDto.ConcertInfoResponse(
                result.concertId(),
                result.concertInfoId(),
                result.concertName(),
                result.concertDate()
        )).toList();
        return new ResponseDto(ResponseCode.SUCC, convert);
    }

    @Operation(summary = "콘서트 좌석 정보 조회", description = "콘서트 좌석 정보를 조회하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertInterfaceDto.ConcertSeatResponse.class)))
    @GetMapping("/info/seats")
    public ResponseDto getConcertSeat(@RequestParam(name = "token") String token) {
        if (token == null || token.isEmpty()) {
            throw new ValidException("token을 입력해 주세요.");
        }
        List<ConcertFacadeDto.GetConcertSeatListResult> result = concertFacade.getConcertSeatList(token);
        return new ResponseDto(ResponseCode.SUCC, result.stream().map(concertSeatInfo -> new ConcertInterfaceDto.ConcertSeatResponse(
                concertSeatInfo.concertInfoId(),
                concertSeatInfo.concertSeatId(),
                concertSeatInfo.seatNumber(),
                concertSeatInfo.seatStatus()
        )));
    }

    @Operation(summary = "콘서트 좌석 임시 점유", description = "콘서트 좌석을 임시 점유하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertInterfaceDto.OccupancySeatResponse.class)))
    @PostMapping("/seats")
    public ResponseDto occupancySeat(@RequestBody ConcertInterfaceDto.OccupancySeatRequest request) {
        ConcertFacadeDto.OccupancyConcertSeatResult result = concertFacade.OccupancyConcertSeat(new ConcertFacadeDto.OccupancyConcertSeatCriteria(request.token(), request.concertSeatId()));
        return new ResponseDto(ResponseCode.SUCC, new ConcertInterfaceDto.OccupancySeatResponse(result.concertSeatId()));
    }

    @Operation(summary = "예약 정보 조회", description = "예약 정보를 조회하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertInterfaceDto.BookingInfoResponse.class)))
    @GetMapping("/booking/info")
    public ResponseDto getBookingInfo(@RequestParam("bookingId") Long bookingId) {
        if (bookingId == null || bookingId <= 0) {
            String message = bookingId == null ? "bookingId를 입력해 주세요." : "accountId의 올바른 범위를 입력해 주세요.";
            throw new ValidException(message);
        }
        BookingDomainDto.GetBookingInfo result = bookingService.getBooking(bookingId);
        return new ResponseDto(ResponseCode.SUCC, new ConcertInterfaceDto.BookingInfoResponse(
                result.bookingId(),
                result.concertName(),
                result.concertDate(),
                result.seatNumber(),
                result.paymentAmount()
        ));
    }

    @Operation(summary = "예약 정보 목록 조회", description = "예약 정보 목록을 조회하는 기능")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertInterfaceDto.BookingInfoListResponse.class)))
    @GetMapping("/booking/info_list")
    public ResponseDto getBookingInfoList(@RequestParam(name = "memberId") Long memberId) {
        if (memberId == null || memberId <= 0) {
            String message = memberId == null ? "memberId를 입력해 주세요." : "accountId의 올바른 범위를 입력해 주세요.";
            throw new ValidException(message);
        }
        List<BookingDomainDto.GetBookingListInfo> result = bookingService.getBookingList(memberId);
        return new ResponseDto(ResponseCode.SUCC, result.stream().map(bookingInfo -> new ConcertInterfaceDto.BookingInfoListResponse(
                bookingInfo.bookingId(),
                bookingInfo.concertName(),
                bookingInfo.concertDate(),
                bookingInfo.seatNumber(),
                bookingInfo.paymentAmount()
        )));
    }


}
