package io.concert_booking.domain.concert.repository;

import io.concert_booking.domain.concert.entity.Performer;

public interface PerformerRepository {

    Performer save(Performer performer);

    Performer getPerformerById(long performerId);

}
