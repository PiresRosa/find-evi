package com.dnd.spaced.domain.word.application.event;

import com.dnd.spaced.domain.word.application.event.dto.request.FoundWordViewCountEvent;
import com.dnd.spaced.domain.word.application.exception.PopularWordScheduleNotFoundException;
import com.dnd.spaced.domain.word.domain.PopularWordMetadata;
import com.dnd.spaced.domain.word.domain.PopularWordSchedule;
import com.dnd.spaced.domain.word.domain.repository.PopularWordRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PopularWordViewCountCalculateEventListener {

    private final Clock clock;
    private final PopularWordRepository popularWordRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void listen(FoundWordViewCountEvent event) {
        LocalDateTime target = LocalDateTime.now(clock);

        popularWordRepository.findBy(event.wordId(), target)
                             .ifPresentOrElse(
                                     PopularWordMetadata::view,
                                     () -> {
                                         PopularWordSchedule schedule = findSchedule(target);
                                         PopularWordMetadata metadata = PopularWordMetadata.builder()
                                                                                        .wordId(event.wordId())
                                                                                        .schedule(schedule)
                                                                                        .build();

                                         popularWordRepository.save(metadata);
                                     }
                             );
    }

    private PopularWordSchedule findSchedule(LocalDateTime target) {
        return popularWordRepository.findBy(target)
                                    .orElseThrow(PopularWordScheduleNotFoundException::new);
    }
}
