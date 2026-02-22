package com.dnd.spaced.domain.comment.application.dto;

import com.dnd.spaced.domain.comment.application.dto.request.CommentConditionInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.CreateCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.DeleteCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.UpdateCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentServiceMapper {

    public static CreateCommentInfoDto ofCreate(String email, Long wordId, String content) {
        return new CreateCommentInfoDto(email, wordId, content);
    }

    public static UpdateCommentInfoDto ofUpdate(String email, Long commentId, String content) {
        return new UpdateCommentInfoDto(email, commentId, content);
    }

    public static DeleteCommentInfoDto of(String email, Long wordId, Long commentId) {
        return new DeleteCommentInfoDto(email, wordId, commentId);
    }

    public static CommentConditionInfoDto to(
            Long wordId,
            Long lastCommentId,
            Integer lastLikeCount,
            Pageable pageable,
            String email
    ) {
        return new CommentConditionInfoDto(wordId, email, lastCommentId, lastLikeCount, pageable);
    }

    public static List<MultipleCommentInfoDto> fromComment(List<CommentInfoWithLikeDto> dtos) {
        return dtos.stream()
                   .map(MultipleCommentInfoDto::from)
                   .toList();
    }

    public static List<MultiplePopularCommentInfoDto> fromPopularComment(List<PopularCommentInfoDto> dtos) {
        return dtos.stream()
                   .map(MultiplePopularCommentInfoDto::from)
                   .toList();
    }
}
