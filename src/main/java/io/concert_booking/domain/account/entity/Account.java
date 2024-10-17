package io.concert_booking.domain.account.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account extends EntityTimestamp {

    public Account(Long memberId, Long balance) {
        this.memberId = memberId;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private Long memberId;

    private Long balance;

    public void chargeBalance(long amount) {
        this.balance += amount;
    }

    public void paymentBalance(long amount) {
        this.balance -= amount;
    }

}
