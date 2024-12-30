package io.concert_booking.infrastructure.account.jpa;

import io.concert_booking.domain.account.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface AccountJpaRepository extends JpaRepository<Account, Long>, AccountQuerydslRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account getAccountByAccountId(long accountId);
}
