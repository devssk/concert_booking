package io.concert_booking.domain.queue.service;

import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.repository.QueueRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRedis queueRedis;

    public QueueDomainDto.RegisterQueueInfo registerQueue(QueueDomainDto.RegisterQueueCommand command) {
        String result = queueRedis.insertWaitQueue(command.concertId(), command.memberId());
        return new QueueDomainDto.RegisterQueueInfo(result);
    }

    public void updateQueueStatus(QueueDomainDto.UpdateQueueStatusCommand command) {
        queueRedis.updateQueueStatus(command.concertId(), command.memberId(), command.queueStatus().name());
    }

    public void deleteQueue(QueueDomainDto.DeleteQueueCommand command) {
        queueRedis.delete(command.concertId(), command.memberId());
    }

    public boolean existsWaitQueue(QueueDomainDto.ExistsWaitQueueCommand command) {
        return queueRedis.existsWaitQueue(command.concertId(), command.memberId());
    }

    public boolean existsPassQueue(QueueDomainDto.ExistsPassQueueCommand command) {
        return queueRedis.existsPassQueue(command.concertId(), command.memberId());
    }

    public QueueDomainDto.GetPassQueueStatusInfo getPassQueueStatus(QueueDomainDto.GetPassQueueStatusCommand command) {
        String queueStatus = queueRedis.getQueueStatus(command.concertId(), command.memberId());
        return new QueueDomainDto.GetPassQueueStatusInfo(queueStatus);
    }

    public List<QueueDomainDto.GetQueueMemberIdInfo> getQueueList(long concertId) {
        List<Long> waitQueueMemberIdList = queueRedis.getAllWaitQueueList(concertId);
        return waitQueueMemberIdList.stream().map(QueueDomainDto.GetQueueMemberIdInfo::new).toList();
    }

    public long getMyQueueRanking(QueueDomainDto.GetMyQueueRankingCommand command) {
        return queueRedis.getMyRank(command.concertId(), command.memberId());
    }


    public void passQueue(long concertId, int passCount) {
        List<Long> waitQueueList = queueRedis.getWaitQueueList(concertId, passCount);
        if (!waitQueueList.isEmpty()) {
            for (Long memberId : waitQueueList) {
                queueRedis.insertEnterQueue(concertId, memberId);
            }
        }
        queueRedis.deleteWaitQueue(concertId, passCount);
    }

}
