package io.concert_booking.domain.queue.repository;

import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;

import java.util.List;

public interface QueueRepository {

    Queue save(Queue queue);

    void delete(Queue queue);

    void deleteQueueById(long queueId);

    Queue getQueueById(long queueId);

    List<Queue> getAllQueueByStatus(long concertId, QueueStatus queueStatus);

    List<Queue> getQueueByStatusIsWait(long concertId, int passCount);

    List<Long> getQueueConcertIdList();

    void passQueue(List<Long> concertIdList);

    void deleteQueueByExpiredTime(int expiredMinute);

}
