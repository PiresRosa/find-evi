package com.dnd.spaced.domain.word.application.event;

import com.dnd.spaced.domain.word.application.event.dto.request.FoundWordInfoEvent;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ViewCountCalculateEventListener {

    private final WordRepository wordRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void listen(FoundWordInfoEvent event) {
        wordRepository.updateViewCount(event.wordId());
    }
}
