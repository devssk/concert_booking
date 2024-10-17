package io.concert_booking.domain.queue.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Queue extends EntityTimestamp {

    public Queue(Long concertId, QueueStatus queueStatus) {
        this.concertId = concertId;
        this.queueStatus = queueStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queueId;

    private Long concertId;

    @Enumerated(EnumType.STRING)
    private QueueStatus queueStatus;

    public void updateQueueStatus(QueueStatus queueStatus) {
        this.queueStatus = queueStatus;
    }

}
