package io.concert_booking.domain.account.service;

import io.concert_booking.domain.account.dto.AccountHistoryDomainDto;
import io.concert_booking.domain.account.entity.AccountHistory;
import io.concert_booking.domain.account.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    public AccountHistoryDomainDto.GetAccountHistoryInfo getAccountHistory(long accountHistoryId) {
        AccountHistory getAccountHistory = accountHistoryRepository.getAccountHistoryById(accountHistoryId);
        return new AccountHistoryDomainDto.GetAccountHistoryInfo(
                getAccountHistory.getAccountHistoryId(),
                getAccountHistory.getAccountId(),
                getAccountHistory.getAccountType().name(),
                getAccountHistory.getAmount(),
                getAccountHistory.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public List<AccountHistoryDomainDto.GetAccountHistoryListInfo> getAccountHistoryList(long accountId) {
        List<AccountHistory> getAccountHistoryList = accountHistoryRepository.getAllAccountHistoryByAccountId(accountId);
        return getAccountHistoryList.stream().map(accountHistory -> new AccountHistoryDomainDto.GetAccountHistoryListInfo(
                accountHistory.getAccountHistoryId(),
                accountHistory.getAccountId(),
                accountHistory.getAccountType().name(),
                accountHistory.getAmount(),
                accountHistory.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))).toList();
    }

}
