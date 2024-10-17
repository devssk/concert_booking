package io.concert_booking.infrastructure.account.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.concert_booking.domain.account.entity.Account;
import org.springframework.stereotype.Repository;

import static io.concert_booking.domain.account.entity.QAccount.*;

public interface AccountQuerydslRepository {

    Account getAccountById(long accountId);

    Account getAccountByMemberId(long memberId);

}

@Repository
class AccountQuerydslRepositoryImpl implements AccountQuerydslRepository {

    private final JPAQueryFactory query;

    public AccountQuerydslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.query = queryFactory;
    }

    @Override
    public Account getAccountById(long accountId) {
        Account result = query
                .select(account)
                .from(account)
                .where(account.accountId.eq(accountId))
                .fetchOne();
        return result;
    }

    @Override
    public Account getAccountByMemberId(long memberId) {
        Account result = query
                .select(account)
                .from(account)
                .where(account.memberId.eq(memberId))
                .fetchOne();
        return result;
    }

}