package com.dnd.spaced.domain.comment.application.dto;

import com.dnd.spaced.domain.comment.application.dto.request.CommentConditionInfoDto;
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

    public static CommentConditionInfoDto to(Long lastCommentId, int lastLikeCount, Pageable pageable, String email) {
        return new CommentConditionInfoDto(lastCommentId, lastLikeCount, pageable, email);
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
