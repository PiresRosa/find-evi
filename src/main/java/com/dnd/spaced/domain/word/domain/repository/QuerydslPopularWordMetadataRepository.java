package com.dnd.spaced.domain.word.domain.repository;

import static com.dnd.spaced.domain.word.domain.QPopularWordMetadata.popularWordMetadata;
import static com.dnd.spaced.domain.word.domain.QPopularWordSchedule.popularWordSchedule;
import static com.dnd.spaced.domain.word.domain.QWord.word;

import com.dnd.spaced.domain.word.domain.PopularWordMetadata;
import com.dnd.spaced.domain.word.domain.PopularWordSchedule;
import com.dnd.spaced.domain.word.domain.Word;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslPopularWordMetadataRepository implements PopularWordRepository {

    private final JPAQueryFactory queryFactory;
    private final PopularWordScheduleCrudRepository scheduleCrudRepository;
    private final PopularWordMetadataCrudRepository metadataCrudRepository;

    @Override
    public void save(PopularWordMetadata popularWordMetadata) {
        metadataCrudRepository.save(popularWordMetadata);
    }

    @Override
    public void save(PopularWordSchedule popularWordSchedule) {
        scheduleCrudRepository.save(popularWordSchedule);
    }

    @Override
    public List<Word> findAllBy(LocalDateTime target, Pageable pageable) {
        List<Long> wordIds = queryFactory.select(popularWordMetadata.wordId)
                                         .from(popularWordMetadata)
                                         .where(
                                                 popularWordMetadata.schedule.startAt.goe(target),
                                                 popularWordMetadata.schedule.endAt.lt(target)
                                         )
                                         .orderBy(popularWordMetadata.viewCount.desc())
                                         .limit(pageable.getPageSize())
                                         .fetch();
        List<Word> result = queryFactory.selectFrom(word)
                                        .where(word.id.in(wordIds.toArray(Long[]::new)))
                                        .fetch();
        Map<Long, Integer> orders = IntStream.range(0, wordIds.size())
                                             .boxed()
                                             .collect(Collectors.toMap(
                                                     wordIds::get,
                                                     Function.identity(),
                                                     (a, b) -> a,
                                                     HashMap::new
                                             ));

        result.sort(Comparator.comparingInt(word -> orders.get(word.getId())));

        return result;
    }

    @Override
    public Optional<PopularWordMetadata> findBy(Long wordId, LocalDateTime target) {
        PopularWordMetadata result = queryFactory.selectFrom(popularWordMetadata)
                                                 .where(popularWordMetadata.wordId.eq(wordId),
                                                         popularWordMetadata.schedule.startAt.goe(target),
                                                         popularWordMetadata.schedule.endAt.lt(target)
                                                 )
                                                 .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<PopularWordSchedule> findBy(LocalDateTime target) {
        PopularWordSchedule result = queryFactory.selectFrom(popularWordSchedule)
                                                 .where(popularWordSchedule.startAt.goe(target),
                                                         popularWordSchedule.endAt.lt(target)
                                                 )
                                                 .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public void deleteBy(LocalDateTime target) {
        queryFactory.delete(popularWordMetadata)
                    .where(popularWordMetadata.schedule.endAt.loe(target))
                    .execute();
        queryFactory.delete(popularWordSchedule)
                    .where(popularWordSchedule.endAt.loe(target))
                    .execute();
    }
}
