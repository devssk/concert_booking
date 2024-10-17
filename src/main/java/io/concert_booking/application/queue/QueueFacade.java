package io.concert_booking.application.queue;

import io.concert_booking.application.queue.dto.QueueFacadeDto;
import io.concert_booking.domain.concert.dto.ConcertDomainDto;
import io.concert_booking.domain.concert.dto.ConcertInfoDomainDto;
import io.concert_booking.domain.concert.service.ConcertInfoService;
import io.concert_booking.domain.concert.service.ConcertService;
import io.concert_booking.domain.member.service.MemberService;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QueueFacade {

    private final QueueService queueService;
    private final TokenService tokenService;
    private final ConcertInfoService concertInfoService;
    private final ConcertService concertService;
    private final MemberService memberService;

    @Transactional
    public QueueFacadeDto.RegisterQueueResult registerQueue(QueueFacadeDto.RegisterQueueCriteria criteria) {
        long concertInfoId = criteria.concertInfoId();
        long memberId = criteria.memberId();

        memberService.getMember(memberId);
        ConcertInfoDomainDto.GetConcertInfoInfo concertInfo = concertInfoService.getConcertInfo(concertInfoId);
        ConcertDomainDto.GetConcertInfo concert = concertService.getConcert(concertInfo.concertId());

        QueueDomainDto.RegisterQueueInfo registerQueueInfo = queueService.registerQueue(new QueueDomainDto.RegisterQueueCommand(concert.concertId()));

        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", concert.concertId());
        payload.put("concertInfoId", concertInfoId);
        payload.put("memberId", memberId);
        payload.put("queueId", registerQueueInfo.queueId());

        String token = tokenService.issueToken(payload);

        return new QueueFacadeDto.RegisterQueueResult(token);
    }

    @Transactional
    public QueueFacadeDto.GetMyQueueNumberResult getMyQueueNumber(String token) {
        Map<String, Long> payload;
        try {
            payload = tokenService.decodeToken(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        long queueId = payload.get("queueId");
        long concertId = payload.get("concertId");

        List<QueueDomainDto.GetQueueListInfo> queueInfoList = queueService.getQueueList(concertId, QueueStatus.WAIT);

        OptionalInt findIndex = IntStream.range(0, queueInfoList.size()).filter(i -> queueInfoList.get(i).queueId() == queueId).findFirst();

        int index = findIndex.isPresent() ? findIndex.getAsInt() : -1;
        if (index < 0) {
            throw new IllegalArgumentException("해당 대기열에 존재 하지 않습니다.");
        }
        QueueDomainDto.GetQueueListInfo getQueueInfo = queueInfoList.get(index);

        int frontQueue = index;
        int myQueueNumber = index + 1;
        int backQueue = queueInfoList.size() - (index + 1);
        String queueStatus = getQueueInfo.queueStatus();

        return new QueueFacadeDto.GetMyQueueNumberResult(frontQueue, myQueueNumber, backQueue, queueStatus);
    }

}
