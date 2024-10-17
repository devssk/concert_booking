package io.concert_booking.infrastructure.account;

import io.concert_booking.domain.account.entity.AccountHistory;
import io.concert_booking.domain.account.repository.AccountHistoryRepository;
import io.concert_booking.infrastructure.account.jpa.AccountHistoryJpaRepository;
import io.concert_booking.infrastructure.exception.EntityRowNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountHistoryRepositoryImpl implements AccountHistoryRepository {

    private final AccountHistoryJpaRepository accountHistoryJpaRepository;

    @Transactional
    @Override
    public AccountHistory save(AccountHistory accountHistory) {
        return accountHistoryJpaRepository.save(accountHistory);
    }

    @Transactional
    @Override
    public AccountHistory getAccountHistoryById(long id) {
        Optional<AccountHistory> find = accountHistoryJpaRepository.findById(id);
        if (find.isPresent()) {
            return find.get();
        } else {
            throw new EntityRowNotFoundException("해당 내역이 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountHistory> getAllAccountHistoryByAccountId(long accountId) {
        return accountHistoryJpaRepository.getAccountHistoryByAccountId(accountId);
    }
}
