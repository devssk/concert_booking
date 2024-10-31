package io.concert_booking.domain.account.service;

import io.concert_booking.common.exception.ConcertBookingException;
import io.concert_booking.common.exception.ErrorCode;
import io.concert_booking.domain.account.dto.AccountDomainDto;
import io.concert_booking.domain.account.entity.Account;
import io.concert_booking.domain.account.entity.AccountHistory;
import io.concert_booking.domain.account.entity.AccountType;
import io.concert_booking.domain.account.repository.AccountHistoryRepository;
import io.concert_booking.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    @Transactional
    public AccountDomainDto.RegisterAccountInfo registerAccount(AccountDomainDto.RegisterAccountCommand command) {
        Account account = new Account(command.memberId(), 0L);
        Account saveAccount = accountRepository.save(account);
        return new AccountDomainDto.RegisterAccountInfo(saveAccount.getAccountId());
    }

    @Transactional(readOnly = true)
    public AccountDomainDto.GetAccountByIdInfo getAccountById(long accountId) {
        Account getAccount = accountRepository.getAccountById(accountId);
        return new AccountDomainDto.GetAccountByIdInfo(getAccount.getAccountId(), getAccount.getBalance());
    }

    @Transactional(readOnly = true)
    public AccountDomainDto.GetAccountByMemberIdInfo getAccountByMemberId(long memberId) {
        Account getAccount = accountRepository.getAccountByMemberId(memberId);
        return new AccountDomainDto.GetAccountByMemberIdInfo(getAccount.getAccountId(), getAccount.getBalance());
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 10, backoff = @Backoff(delay = 500))
    @Transactional
    public AccountDomainDto.ChargeAccountInfo chargeAccount(AccountDomainDto.ChargeAccountCommand command) {
        Account getAccount = accountRepository.getAccountById(command.accountId());
        getAccount.chargeBalance(command.amount());

        AccountHistory accountHistory = new AccountHistory(getAccount.getAccountId(), AccountType.CHARGE, command.amount());
        accountHistoryRepository.save(accountHistory);

        return new AccountDomainDto.ChargeAccountInfo(getAccount.getAccountId(), accountHistory.getAmount(), getAccount.getBalance());
    }

    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 10, backoff = @Backoff(delay = 500))
    @Transactional
    public AccountDomainDto.PaymentAccountInfo paymentAccount(AccountDomainDto.PaymentAccountCommand command) {
        Account getAccount = accountRepository.getAccountById(command.accountId());
        Long balance = getAccount.getBalance();
        Long amount = command.amount();
        if (balance - amount < 0) {
            throw new ConcertBookingException(ErrorCode.NOT_ENOUGH_BALANCE);
        }
        getAccount.paymentBalance(command.amount());

        AccountHistory accountHistory = new AccountHistory(getAccount.getAccountId(), AccountType.PAYMENT, command.amount());
        accountHistoryRepository.save(accountHistory);

        return new AccountDomainDto.PaymentAccountInfo(getAccount.getAccountId(), accountHistory.getAccountHistoryId(), accountHistory.getAmount(), getAccount.getBalance());
    }

    @Recover
    public void recover(ObjectOptimisticLockingFailureException e) {
        throw new ConcertBookingException(ErrorCode.FAIL_UPDATE_ACCOUNT);
    }

}
