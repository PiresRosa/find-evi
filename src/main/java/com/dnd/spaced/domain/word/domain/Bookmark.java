package com.dnd.spaced.domain.word.domain;

import com.dnd.spaced.global.entity.CreateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "bookmarks")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Bookmark extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "account_id")
    private Long accountId;

    @JoinColumn(name = "word_id")
    private Long wordId;

    @Builder
    private Bookmark(Long accountId, Long wordId) {
        this.accountId = accountId;
        this.wordId = wordId;
    }
}
