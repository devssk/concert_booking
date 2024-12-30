package io.concert_booking.infrastructure.account;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.account.entity.AccountHistory;
import io.concert_booking.domain.account.repository.AccountHistoryRepository;
import io.concert_booking.infrastructure.account.jpa.AccountHistoryJpaRepository;
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
            throw new ConcertBookingException(ErrorCode.NOT_FOUND_ENTITY);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountHistory> getAllAccountHistoryByAccountId(long accountId) {
        return accountHistoryJpaRepository.getAccountHistoryByAccountId(accountId);
    }
}
