package io.concert_booking.domain.concert.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ConcertSeat extends EntityTimestamp {

    public ConcertSeat(Long concertInfoId, Integer seatNumber, SeatStatus seatStatus) {
        this.concertInfoId = concertInfoId;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertSeatId;

    private Long concertInfoId;

    private Long memberId;

    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Version
    private int version;

    public void updateSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public void occupancySeat(long memberId) {
        this.memberId = memberId;
        this.seatStatus = SeatStatus.OCCUPANCY;
    }

    public void expireOccupancySeat() {
        this.memberId = null;
        this.seatStatus = SeatStatus.OPEN;
    }

}
