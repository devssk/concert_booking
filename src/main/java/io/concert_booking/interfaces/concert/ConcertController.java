package io.concert_booking.interfaces.concert;

import io.concert_booking.interfaces.ResponseCode;
import io.concert_booking.interfaces.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @GetMapping("/{concertId}/info")
    public ResponseDto getConcertInfo(@PathVariable(name = "concertId") Long concertId) {
        List<ConcertDto.ConcertInfoResponse> list = new ArrayList<>();
        list.add(new ConcertDto.ConcertInfoResponse(1L, 1L, "2024 IU CONCERT HER", "2024-03-02"));
        list.add(new ConcertDto.ConcertInfoResponse(1L, 2L, "2024 IU CONCERT HER", "2024-03-03"));
        return new ResponseDto(ResponseCode.SUCC, list);
    }

    @GetMapping("/{concertId}/info/{concertInfoId}/seats")
    public ResponseDto getConcertSeat(@PathVariable(name = "concertId") Long concertId, @PathVariable(name = "concertInfoId") Long concertInfoId) {
        List<ConcertDto.ConcertSeatResponse> list = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            list.add(new ConcertDto.ConcertSeatResponse(1L, (long) i, i, "OPEN"));
        }
        return new ResponseDto(ResponseCode.SUCC, list);
    }

    @PostMapping("/seats")
    public ResponseDto bookingSeat(@RequestBody ConcertDto.BookingSeatRequest request) {
        return new ResponseDto(ResponseCode.SUCC, new ConcertDto.BookingSeatResponse(1L, 5));
    }

    @GetMapping("/booking/{memberId}/info/{bookingId}")
    public ResponseDto getBookingInfo(@PathVariable(name = "memberId") Long memberId, @PathVariable("bookingId") Long bookingId) {
        return new ResponseDto(ResponseCode.SUCC, new ConcertDto.BookingInfoResponse(1L, "2024 IU CONCERT HER", "2024-03-03", 1, 169000L));
    }

    @GetMapping("/booking/{memberId}/info")
    public ResponseDto getBookingInfoList(@PathVariable(name = "memberId") Long memberId) {
        List<ConcertDto.BookingInfoListResponse> list = new ArrayList<>();
        list.add(new ConcertDto.BookingInfoListResponse(2L, "2024 IU CONCERT HER", "2024-03-02", 1, 169000L));
        list.add(new ConcertDto.BookingInfoListResponse(1L, "2024 IU CONCERT HER", "2024-03-03", 1, 169000L));
        return new ResponseDto(ResponseCode.SUCC, list);
    }


}
