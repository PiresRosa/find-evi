package com.dnd.spaced.domain.comment.domain;

import com.dnd.spaced.domain.comment.domain.exception.InvalidContentException;
import com.dnd.spaced.global.entity.BaseTimeEntity;
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

@Table(name = "comments")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Comment extends BaseTimeEntity {

    private static final int CONTENT_MIN_LENGTH = 1;
    private static final int CONTENT_MAX_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "account_id")
    private Long accountId;

    @JoinColumn(name = "word_id")
    private Long wordId;

    private String content;

    private int likeCount = 0;

    @Builder
    private Comment(Long accountId, Long wordId, String content) {
        if (isInvalidContent(content)) {
            throw new InvalidContentException();
        }

        this.accountId = accountId;
        this.wordId = wordId;
        this.content = content;
    }

    private boolean isInvalidContent(String content) {
        return content == null || content.isBlank()
                || !(CONTENT_MIN_LENGTH <= content.length() && content.length() <= CONTENT_MAX_LENGTH);
    }

    public boolean isOwner(Long accountId) {
        return this.accountId.equals(accountId);
    }

    public void changeContent(String content) {
        if (isInvalidContent(content)) {
            throw new InvalidContentException();
        }

        this.content = content;
    }

    public void addLike() {
        this.likeCount++;
    }

    public void removeLike() {
        this.likeCount--;
    }
}
