package io.concert_booking.domain.concert.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class ConcertInfo extends EntityTimestamp {

    public ConcertInfo(Long concertId, LocalDate concertDate, Integer capacity) {
        this.concertId = concertId;
        this.concertDate = concertDate;
        this.capacity = capacity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertInfoId;

    private Long concertId;

    private LocalDate concertDate;

    private Integer capacity;

}
