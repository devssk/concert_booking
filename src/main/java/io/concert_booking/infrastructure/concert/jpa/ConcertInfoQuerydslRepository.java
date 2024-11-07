package io.concert_booking.infrastructure.concert.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

import static io.concert_booking.domain.concert.entity.QConcertInfo.*;

public interface ConcertInfoQuerydslRepository {
    List<Long> getAllConcertInfoByConcertDate(LocalDate concertDate);
}

@Repository
class ConcertInfoQuerydslRepositoryImpl implements ConcertInfoQuerydslRepository {

    private final JPAQueryFactory query;

    ConcertInfoQuerydslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.query = queryFactory;
    }

    @Override
    public List<Long> getAllConcertInfoByConcertDate(LocalDate concertDate) {
        List<Long> result = query.select(concertInfo.concertInfoId).distinct()
                .from(concertInfo)
                .where(concertInfo.concertDate.eq(concertDate))
                .fetch();
        return result;
    }
}

