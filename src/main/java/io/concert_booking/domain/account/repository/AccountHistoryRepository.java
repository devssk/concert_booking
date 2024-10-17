package io.concert_booking.domain.account.repository;

import io.concert_booking.domain.account.entity.AccountHistory;

import java.util.List;

public interface AccountHistoryRepository {

    AccountHistory save(AccountHistory accountHistory);

    AccountHistory getAccountHistoryById(long id);

    List<AccountHistory> getAllAccountHistoryByAccountId(long accountId);

}
