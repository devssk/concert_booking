package io.concert_booking.domain.account.dto;

public class AccountHistoryDomainDto {

    public record GetAccountHistoryInfo(
            long accountHistoryId,
            long accountId,
            String accountType,
            long amount,
            String createdAt
    ) {}

    public record GetAccountHistoryListInfo(
            long accountHistoryId,
            long accountId,
            String accountType,
            long amount,
            String createdAt
    ) {}

}
