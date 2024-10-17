package io.concert_booking.domain.queue.service;

import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.Queue;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;

    @Transactional
    public QueueDomainDto.RegisterQueueInfo registerQueue(QueueDomainDto.RegisterQueueCommand command) {
        Queue queue = new Queue(command.concertId(), QueueStatus.WAIT);
        Queue saveQueue = queueRepository.save(queue);
        return new QueueDomainDto.RegisterQueueInfo(saveQueue.getQueueId());
    }

    @Transactional
    public QueueDomainDto.UpdateQueueStatusInfo updateQueueStatus(QueueDomainDto.UpdateQueueStatusCommand command) {
        Queue getQueue = queueRepository.getQueueById(command.queueId());
        getQueue.updateQueueStatus(command.queueStatus());

        return new QueueDomainDto.UpdateQueueStatusInfo(getQueue.getQueueId(), getQueue.getQueueStatus().name());
    }

    @Transactional
    public void deleteQueue(long queueId) {
        queueRepository.deleteQueueById(queueId);
    }

    public QueueDomainDto.GetQueueInfo getQueue(long queueId) {
        Queue getQueue = queueRepository.getQueueById(queueId);
        return new QueueDomainDto.GetQueueInfo(
                getQueue.getQueueId(),
                getQueue.getConcertId(),
                getQueue.getQueueStatus().name(),
                getQueue.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public List<QueueDomainDto.GetQueueListInfo> getQueueList(long concertId, QueueStatus queueStatus) {
        List<Queue> getQueueList = queueRepository.getAllQueueByStatus(concertId, queueStatus);
        return getQueueList.stream().map(queue -> new QueueDomainDto.GetQueueListInfo(
                queue.getQueueId(),
                queue.getConcertId(),
                queue.getQueueStatus().name(),
                queue.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ))).toList();
    }

    public List<Long> getQueueConcertIdList() {
        return queueRepository.getQueueConcertIdList();
    }

    @Transactional
    public void passQueue(long concertId, int passCount) {
        List<Queue> getQueueList = queueRepository.getQueueByStatusIsWait(concertId, passCount);
        List<Long> queueIdList = getQueueList.stream().map(Queue::getQueueId).toList();
        queueRepository.passQueue(queueIdList);
    }

    @Transactional
    public void getOutQueue(int expiredMinute) {
        queueRepository.deleteQueueByExpiredTime(expiredMinute);
    }

}
