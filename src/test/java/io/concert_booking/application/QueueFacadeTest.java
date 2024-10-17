package io.concert_booking.application;

import io.concert_booking.application.queue.QueueFacade;
import io.concert_booking.application.queue.dto.QueueFacadeDto;
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
        payload.put("queueId", 8L);
        payload.put("concertId", 1L);
        List<QueueDomainDto.GetQueueListInfo> queueInfoList = new ArrayList<>();
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(1L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:12"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(2L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:13"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(3L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:14"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(4L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:15"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(5L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:18"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(6L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:19"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(7L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:20"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(8L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:21"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(9L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:22"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(10L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:24"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(11L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:27"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(12L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:28"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(13L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:30"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(14L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:32"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(15L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:33"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(16L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:34"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(17L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:35"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(18L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:40"));
        doReturn(payload).when(tokenService).decodeToken(anyString());
        doReturn(queueInfoList).when(queueService).getQueueList(anyLong(), any());

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
    @DisplayName("대기열 가져오기 - 토큰 오류")
    void getMyQueueNumberTest02() throws Exception {
        // given
        String token = "tempToken";
        doThrow(new Exception()).when(tokenService).decodeToken(anyString());

        // when
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> queueFacade.getMyQueueNumber(token));

        // then
        assertEquals("유효한 토큰이 아닙니다.", throwable.getMessage());
    }

    @Test
    @DisplayName("대기열 가져오기 - 대기열에 존재하지 않는 경우")
    void getMyQueueNumberTest03() throws Exception {
        // given
        String token = "tempToken";
        Map<String, Long> payload = new HashMap<>();
        payload.put("queueId", 20L);
        payload.put("concertId", 1L);
        List<QueueDomainDto.GetQueueListInfo> queueInfoList = new ArrayList<>();
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(1L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:12"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(2L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:13"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(3L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:14"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(4L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:15"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(5L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:18"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(6L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:19"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(7L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:20"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(8L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:21"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(9L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:22"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(10L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:24"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(11L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:27"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(12L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:28"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(13L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:30"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(14L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:32"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(15L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:33"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(16L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:34"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(17L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:35"));
        queueInfoList.add(new QueueDomainDto.GetQueueListInfo(18L, 1L, QueueStatus.WAIT.name(), "2024-10-17 17:13:40"));
        doReturn(payload).when(tokenService).decodeToken(anyString());
        doReturn(queueInfoList).when(queueService).getQueueList(anyLong(), any());

        // when
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> queueFacade.getMyQueueNumber(token));

        // then
        assertEquals("해당 대기열에 존재 하지 않습니다.", throwable.getMessage());
    }

}