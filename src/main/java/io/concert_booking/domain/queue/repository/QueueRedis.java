package io.concert_booking.domain.queue.repository;

import java.util.List;

public interface QueueRedis {

    String insertWaitQueue(long concertId, long memberId);

    void insertEnterQueue(long concertId, long memberId);

    void delete(long concertId, long memberId);

    long getMyRank(long concertId, long memberId);

    List<Long> getAllWaitQueueList(long concertId);

    List<Long> getWaitQueueList(long concertId, int count);

    void deleteWaitQueue(long concertId, int count);

    void updateQueueStatus(long concertId, long memberId, String queueStatus);

    boolean existsWaitQueue(long concertId, long memberId);

    boolean existsPassQueue(long concertId, long memberId);

    String getQueueStatus(long concertId, long memberId);

}
