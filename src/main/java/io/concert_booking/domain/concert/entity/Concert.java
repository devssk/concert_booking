package io.concert_booking.domain.concert.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Concert extends EntityTimestamp {

    public Concert(Long performerId, String concertName) {
        this.performerId = performerId;
        this.concertName = concertName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertId;

    private Long performerId;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String concertName;

}
