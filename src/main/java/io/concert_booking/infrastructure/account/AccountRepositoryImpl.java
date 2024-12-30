package io.concert_booking.infrastructure.account;

import io.concert_booking.domain.account.entity.Account;
import io.concert_booking.domain.account.repository.AccountRepository;
import io.concert_booking.infrastructure.account.jpa.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Transactional
    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public Account getAccountById(long accountId) {
        return accountJpaRepository.getAccountById(accountId);
    }

    @Transactional(readOnly = true)
    @Override
    public Account getAccountByMemberId(long memberId) {
        return accountJpaRepository.getAccountByMemberId(memberId);
    }

    @Transactional
    @Override
    public Account getAccountByIdForUpdate(long accountId) {
        return accountJpaRepository.getAccountByAccountId(accountId);
    }

}
