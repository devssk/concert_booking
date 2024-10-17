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
public class Performer extends EntityTimestamp {

    public Performer(String performerName) {
        this.performerName = performerName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performerId;

    private String performerName;

}
