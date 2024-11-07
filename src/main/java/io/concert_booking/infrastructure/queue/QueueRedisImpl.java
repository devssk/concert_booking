package io.concert_booking.infrastructure.queue;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.repository.QueueRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class QueueRedisImpl implements QueueRedis {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String QUEUE_PREFIX = "queue:";

    @Override
    public String insertWaitQueue(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId;
        long time = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(queueKey, memberId, time);
        return queueKey;
    }

    @Override
    public void insertEnterQueue(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId + ":" + memberId;
        redisTemplate.opsForValue().set(queueKey, QueueStatus.ENTER.name(), 20L, TimeUnit.MINUTES);
    }

    @Override
    public void delete(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId + ":" + memberId;
        redisTemplate.delete(queueKey);
    }

    @Override
    public long getMyRank(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId;
        Long rank = redisTemplate.opsForZSet().rank(queueKey, memberId);
        if (rank == null) {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_QUEUE);
        }
        return rank;
    }

    @Override
    public List<Long> getAllWaitQueueList(long concertId) {
        String queueKey = QUEUE_PREFIX + concertId;
        Set<Object> values = redisTemplate.opsForZSet().range(queueKey, 0, -1);
        List<Long> result = new ArrayList<>();
        for (Object value : Objects.requireNonNull(values)) {
            if (value instanceof Long) {
                result.add((Long) value);
            }
        }
        return result;
    }

    @Override
    public List<Long> getWaitQueueList(long concertId, int count) {
        String queueKey = QUEUE_PREFIX + concertId;
        Set<Object> values = redisTemplate.opsForZSet().range(queueKey, 0, count - 1);
        List<Long> result = new ArrayList<>();
        for (Object value : Objects.requireNonNull(values)) {
            if (value instanceof Long) {
                result.add((Long) value);
            }
        }
        return result;
    }

    @Override
    public void deleteWaitQueue(long concertId, int count) {
        String queueKey = QUEUE_PREFIX + concertId;
        redisTemplate.opsForZSet().removeRange(queueKey, 0, count - 1);
    }

    @Override
    public void updateQueueStatus(long concertId, long memberId, String queueStatus) {
        String queueKey = QUEUE_PREFIX + concertId + ":" + memberId;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(queueKey))) {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_QUEUE);
        }
        redisTemplate.opsForValue().set(queueKey, queueStatus, 20L, TimeUnit.MINUTES);
    }

    @Override
    public boolean existsWaitQueue(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId;
        Double score = redisTemplate.opsForZSet().score(queueKey, memberId);
        return score != null;
    }

    @Override
    public boolean existsPassQueue(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId + ":" + memberId;
        Boolean result = redisTemplate.hasKey(queueKey);
        return result != null && result;
    }

    @Override
    public String getQueueStatus(long concertId, long memberId) {
        String queueKey = QUEUE_PREFIX + concertId + ":" + memberId;
        Object object = redisTemplate.opsForValue().get(queueKey);
        if (object instanceof String) {
            return (String) object;
        } else {
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_QUEUE);
        }
    }

}
