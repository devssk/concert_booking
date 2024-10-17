package io.concert_booking.domain.member.entity;

import io.concert_booking.domain.EntityTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends EntityTimestamp {

    public Member(String memberName) {
        this.memberName = memberName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String memberName;

}
