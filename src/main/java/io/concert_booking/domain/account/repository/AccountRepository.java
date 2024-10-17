package io.concert_booking.domain.account.repository;


import io.concert_booking.domain.account.entity.Account;

public interface AccountRepository {

    Account save(Account account);

    Account getAccountById(long accountId);

    Account getAccountByMemberId(long memberId);

    Account getAccountByIdForUpdate(long accountId);

}
