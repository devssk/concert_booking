package io.concert_booking.domain.concert.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Booking extends EntityTimestamp {

    public Booking(Long performerId, Long concertId, Long concertInfoId, Long concertSeatId, Long memberId, Long accountHistoryId, String performerName, String concertName, String concertDate, Integer seatNumber, Long paymentAmount, String memberName) {
        this.performerId = performerId;
        this.concertId = concertId;
        this.concertInfoId = concertInfoId;
        this.concertSeatId = concertSeatId;
        this.memberId = memberId;
        this.accountHistoryId = accountHistoryId;
        this.performerName = performerName;
        this.concertName = concertName;
        this.concertDate = concertDate;
        this.seatNumber = seatNumber;
        this.paymentAmount = paymentAmount;
        this.memberName = memberName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long performerId;

    private Long concertId;

    private Long concertInfoId;

    private Long concertSeatId;

    private Long memberId;

    private Long accountHistoryId;

    private String performerName;

    private String concertName;

    private String concertDate;

    private Integer seatNumber;

    private Long paymentAmount;

    private String memberName;


}
