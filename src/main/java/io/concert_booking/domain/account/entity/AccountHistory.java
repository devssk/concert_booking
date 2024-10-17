package io.concert_booking.domain.account.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AccountHistory extends EntityTimestamp {

    public AccountHistory(Long accountId, AccountType accountType, long amount) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountHistoryId;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private long amount;

}
