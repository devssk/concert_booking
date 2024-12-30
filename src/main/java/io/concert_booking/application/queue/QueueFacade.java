package io.concert_booking.application.queue;

import io.concert_booking.application.queue.dto.QueueFacadeDto;
import io.concert_booking.domain.concert.dto.ConcertDomainDto;
import io.concert_booking.domain.concert.dto.ConcertInfoDomainDto;
import io.concert_booking.domain.concert.service.ConcertInfoService;
import io.concert_booking.domain.concert.service.ConcertService;
import io.concert_booking.domain.member.service.MemberService;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        QueueDomainDto.RegisterQueueInfo registerQueueInfo = queueService.registerQueue(new QueueDomainDto.RegisterQueueCommand(concert.concertId(), memberId));

        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", concert.concertId());
        payload.put("concertInfoId", concertInfoId);
        payload.put("memberId", memberId);

        String token = tokenService.issueToken(payload);

        return new QueueFacadeDto.RegisterQueueResult(token);
    }

    @Transactional
    public QueueFacadeDto.GetMyQueueNumberResult getMyQueueNumber(String token) {
        Map<String, Long> payload = tokenService.decodeToken(token);

        long concertId = payload.get("concertId");
        long memberId = payload.get("memberId");

        List<QueueDomainDto.GetQueueMemberIdInfo> queueMemberIdList = queueService.getQueueList(concertId);

        long myQueueRanking = queueService.getMyQueueRanking(new QueueDomainDto.GetMyQueueRankingCommand(concertId, memberId));

        int frontQueue = (int) myQueueRanking;
        int myQueueNumber = (int) myQueueRanking + 1;
        int backQueue = queueMemberIdList.size() - ((int) myQueueRanking + 1);

        return new QueueFacadeDto.GetMyQueueNumberResult(frontQueue, myQueueNumber, backQueue);
    }

}
