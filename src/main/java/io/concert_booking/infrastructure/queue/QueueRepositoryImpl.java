package io.concert_booking.infrastructure.queue;

import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.repository.QueueRepository;
import io.concert_booking.infrastructure.exception.EntityRowNotFoundException;
import io.concert_booking.infrastructure.queue.jpa.QueueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Transactional
    @Override
    public Queue save(Queue queue) {
        return queueJpaRepository.save(queue);
    }

    @Transactional
    @Override
    public void delete(Queue queue) {
        queueJpaRepository.delete(queue);
    }

    @Transactional
    @Override
    public void deleteQueueById(long queueId) {
        queueJpaRepository.deleteById(queueId);
    }

    @Transactional(readOnly = true)
    @Override
    public Queue getQueueById(long queueId) {
        Optional<Queue> find = queueJpaRepository.findById(queueId);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new EntityRowNotFoundException("해당 대기열이 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Queue> getAllQueueByStatus(long concertId, QueueStatus queueStatus) {
        return queueJpaRepository.getAllQueueByStatus(concertId, queueStatus);
    }

    @Override
    public List<Queue> getQueueByStatusIsWait(long concertId, int passCount) {
        return queueJpaRepository.getQueueByStatusIsWait(concertId, passCount);
    }

    @Override
    public List<Long> getQueueConcertIdList() {
        return queueJpaRepository.getQueueConcertIdList();
    }

    @Transactional
    @Override
    public void passQueue(List<Long> concertIdList) {
        queueJpaRepository.passQueue(concertIdList);
    }

    @Override
    public void deleteQueueByExpiredTime(int expiredMinute) {
        queueJpaRepository.deleteQueueByExpiredTime(expiredMinute);
    }
}
