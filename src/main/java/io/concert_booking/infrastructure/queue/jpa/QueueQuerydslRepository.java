package io.concert_booking.infrastructure.queue.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static io.concert_booking.domain.queue.entity.QQueue.*;

public interface QueueQuerydslRepository {
    List<Queue> getAllQueueByStatus(long concertId, QueueStatus status);
    List<Long> getQueueConcertIdList();
    void deleteQueueById(long queueId);
    List<Queue> getQueueByStatusIsWait(long concertId, int passCount);
    void passQueue(List<Long> concertIdList);
    void deleteQueueByExpiredTime(int expiredMinute);
}

@Repository
class QueueQuerydslRepositoryImpl implements QueueQuerydslRepository {

    private final JPAQueryFactory query;

    public QueueQuerydslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.query = queryFactory;
    }

    @Override
    public List<Queue> getAllQueueByStatus(long concertId, QueueStatus status) {
        List<Queue> result = query
                .select(queue)
                .from(queue)
                .where(queue.concertId.eq(concertId).and(queue.queueStatus.eq(status)))
                .orderBy(queue.createdAt.asc())
                .fetch();
        return result;
    }

    @Override
    public List<Long> getQueueConcertIdList() {
        List<Long> result = query
                .select(queue.concertId).distinct()
                .from(queue)
                .fetch();
        return result;
    }

    @Override
    public void deleteQueueById(long queueId) {
        query
                .delete(queue)
                .where(queue.queueId.eq(queueId))
                .execute();
    }

    @Override
    public List<Queue> getQueueByStatusIsWait(long concertId, int passCount) {
        List<Queue> result = query
                .select(queue)
                .from(queue)
                .where(queue.concertId.eq(concertId))
                .orderBy(queue.createdAt.asc())
                .limit(passCount)
                .fetch();
        return result;
    }

    @Override
    public void passQueue(List<Long> concertIdList) {
        query
                .update(queue)
                .set(queue.queueStatus, QueueStatus.WAIT)
                .where(queue.concertId.in(concertIdList));

    }

    @Override
    public void deleteQueueByExpiredTime(int expiredMinute) {
        query
                .delete(queue)
                .where(queue.createdAt.before(LocalDateTime.now().minusSeconds(expiredMinute * 60L)))
                .execute();
    }

}
