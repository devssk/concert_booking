package io.concert_booking.application;

import io.concert_booking.application.queue.QueueFacade;
import io.concert_booking.application.queue.dto.QueueFacadeDto;
import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.domain.queue.dto.QueueDomainDto;
import io.concert_booking.domain.queue.entity.QueueStatus;
import io.concert_booking.domain.queue.service.QueueService;
import io.concert_booking.domain.queue.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueFacadeTest {

    @InjectMocks
    QueueFacade queueFacade;

    @Mock
    QueueService queueService;
    @Mock
    TokenService tokenService;


    @Test
    @DisplayName("대기열 가져오기 - 정상")
    void getMyQueueNumberTest01() throws Exception {
        // given
        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", 1L);
        payload.put("memberId", 1L);
        List<QueueDomainDto.GetQueueMemberIdInfo> queueMemberIdList = new ArrayList<>();
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(9L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(2L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(6L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(5L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(4L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(1L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(3L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(7L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(8L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(12L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(11L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(13L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(15L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(14L));
        doReturn(payload).when(tokenService).decodeToken(anyString());
        doReturn(queueMemberIdList).when(queueService).getQueueList(anyLong());

        // when
        QueueFacadeDto.GetMyQueueNumberResult result = queueFacade.getMyQueueNumber("");

        // then
        assertAll(() -> {
            assertEquals(7, result.frontQueue());
            assertEquals(8, result.myQueueNumber());
            assertEquals(10, result.backQueue());
        });
    }

    @Test
    @DisplayName("대기열 가져오기 - 대기열에 존재하지 않는 경우")
    void getMyQueueNumberTest03() throws Exception {
        // given
        String token = "tempToken";
        Map<String, Long> payload = new HashMap<>();
        payload.put("concertId", 1L);
        payload.put("memberId", 16L);
        List<QueueDomainDto.GetQueueMemberIdInfo> queueMemberIdList = new ArrayList<>();
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(9L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(2L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(6L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(5L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(4L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(1L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(3L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(7L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(8L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(12L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(11L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(13L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(15L));
        queueMemberIdList.add(new QueueDomainDto.GetQueueMemberIdInfo(14L));
        doReturn(payload).when(tokenService).decodeToken(anyString());
        doReturn(queueMemberIdList).when(queueService).getQueueList(anyLong());

        // when
        Throwable throwable = assertThrows(ConcertBookingException.class, () -> queueFacade.getMyQueueNumber(token));

        // then
        assertEquals("대기열에서 찾을 수 없음", throwable.getMessage());
    }

}