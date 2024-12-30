package io.concert_booking.infrastructure.concert.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.concert_booking.domain.concert.entity.SeatStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static io.concert_booking.domain.concert.entity.QConcertSeat.*;

public interface ConcertSeatQuerydslRepository {
    void updateConcertSeatStatusOccupancyExpired();
}

@Repository
class ConcertSeatQuerydslRepositoryImpl implements ConcertSeatQuerydslRepository {

    private final JPAQueryFactory query;

    ConcertSeatQuerydslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.query = queryFactory;
    }

    @Override
    public void updateConcertSeatStatusOccupancyExpired() {
        query
                .update(concertSeat)
                .setNull(concertSeat.memberId)
                .set(concertSeat.seatStatus, SeatStatus.OPEN)
                .where(concertSeat.seatStatus.eq(SeatStatus.OCCUPANCY)
                        .and(concertSeat.updatedAt.before(LocalDateTime.now().minusSeconds(300))))
                .execute();
    }
}
