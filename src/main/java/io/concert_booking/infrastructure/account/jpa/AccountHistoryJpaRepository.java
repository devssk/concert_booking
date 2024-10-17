package io.concert_booking.infrastructure.account.jpa;

import io.concert_booking.domain.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryJpaRepository extends JpaRepository<AccountHistory, Long> {

    List<AccountHistory> getAccountHistoryByAccountId(Long accountId);

}
