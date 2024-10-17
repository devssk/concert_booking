package io.concert_booking.domain.concert.service;

import io.concert_booking.domain.concert.dto.PerformerDomainDto;
import io.concert_booking.domain.concert.entity.Performer;
import io.concert_booking.domain.concert.repository.PerformerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformerService {

    private final PerformerRepository performerRepository;

    public PerformerDomainDto.RegisterPerformerInfo registerPerformer(PerformerDomainDto.RegisterPerformerCommand command) {
        Performer performer = new Performer(command.performerName());
        Performer savePerformer = performerRepository.save(performer);
        return new PerformerDomainDto.RegisterPerformerInfo(savePerformer.getPerformerId());
    }

    public PerformerDomainDto.GetPerformerInfo getPerformer(long performerId) {
        Performer getPerformer = performerRepository.getPerformerById(performerId);
        return new PerformerDomainDto.GetPerformerInfo(getPerformer.getPerformerId(), getPerformer.getPerformerName());
    }

}
