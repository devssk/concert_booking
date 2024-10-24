package io.concert_booking.infrastructure;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.infrastructure.member.MemberRepositoryImpl;
import io.concert_booking.infrastructure.member.jpa.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryImplTest {

    @InjectMocks
    MemberRepositoryImpl memberRepository;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("멤버 가져오기 - 멤버가 없을 경우")
    void getMemberTest() {
        // given
        long memberId = 1L;
        doReturn(Optional.empty()).when(memberJpaRepository).findById(anyLong());

        // when
        Throwable throwable = assertThrows(ConcertBookingException.class, () -> memberRepository.getMemberById(memberId));

        // then
        assertEquals("해당 ID로 찾을 수 없음", throwable.getMessage());
    }

}