package com.dnd.spaced.domain.word.application.schedule;

import com.dnd.spaced.domain.word.domain.PopularWordSchedule;
import com.dnd.spaced.domain.word.domain.repository.PopularWordRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PopularWordScheduler {

    private static final long POPULAR_LIFE_CYCLE_HOURS = 12L;

    private final Clock clock;
    private final PopularWordRepository popularWordRepository;

    @Transactional
    @Scheduled(cron = "0 0 0,12 * * ?")
    public void schedule() {
        LocalDateTime target = LocalDateTime.now(clock);

        popularWordRepository.deleteBy(target);

        PopularWordSchedule schedule = PopularWordSchedule.builder()
                                                          .startAt(target)
                                                          .endAt(target.plusHours(POPULAR_LIFE_CYCLE_HOURS))
                                                          .build();

        popularWordRepository.save(schedule);
    }
}
