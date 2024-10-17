package io.concert_booking.domain.concert.service;

import io.concert_booking.domain.concert.dto.BookingDomainDto;
import io.concert_booking.domain.concert.entity.Booking;
import io.concert_booking.domain.concert.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingDomainDto.RegisterBookingInfo registerBooking(BookingDomainDto.RegisterBookingCommand command) {
        Booking booking = new Booking(
                command.performerId(),
                command.concertId(),
                command.concertInfoId(),
                command.concertSeatId(),
                command.memberId(),
                command.accountHistoryId(),
                command.performerName(),
                command.concertName(),
                command.concertDate(),
                command.seatNumber(),
                command.paymentAmount(),
                command.memberName());
        Booking saveBooking = bookingRepository.save(booking);
        return new BookingDomainDto.RegisterBookingInfo(saveBooking.getBookingId());
    }

    public BookingDomainDto.GetBookingInfo getBooking(long bookingId) {
        Booking getBooking = bookingRepository.getBookingById(bookingId);
        return new BookingDomainDto.GetBookingInfo(
                getBooking.getBookingId(),
                getBooking.getPerformerId(),
                getBooking.getConcertId(),
                getBooking.getConcertInfoId(),
                getBooking.getConcertSeatId(),
                getBooking.getMemberId(),
                getBooking.getAccountHistoryId(),
                getBooking.getPerformerName(),
                getBooking.getConcertName(),
                getBooking.getConcertDate(),
                getBooking.getSeatNumber(),
                getBooking.getPaymentAmount(),
                getBooking.getMemberName(),
                getBooking.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    public List<BookingDomainDto.GetBookingListInfo> getBookingList(long memberId) {
        List<Booking> getBookingList = bookingRepository.getAllBookingByMemberId(memberId);
        return getBookingList.stream().map(booking -> new BookingDomainDto.GetBookingListInfo(
                booking.getBookingId(),
                booking.getPerformerId(),
                booking.getConcertId(),
                booking.getConcertInfoId(),
                booking.getConcertSeatId(),
                booking.getMemberId(),
                booking.getAccountHistoryId(),
                booking.getPerformerName(),
                booking.getConcertName(),
                booking.getConcertDate(),
                booking.getSeatNumber(),
                booking.getPaymentAmount(),
                booking.getMemberName(),
                booking.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )).toList();
    }

}
