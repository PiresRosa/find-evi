package com.dnd.spaced.domain.comment.application.dto;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.comment.application.dto.request.CommentConditionInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.CreateCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.DeleteCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.ReadCommentAllByLikedDto;
import com.dnd.spaced.domain.comment.application.dto.request.ReadCommentAllByWrittenDto;
import com.dnd.spaced.domain.comment.application.dto.request.UpdateCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.LikedCommentDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.WrittenCommentDto;
import com.dnd.spaced.domain.comment.domain.Comment;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.FindCommentAllByLikedConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.FindCommentAllByWrittenConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentWithoutIsLikeDto;
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

    public static List<MultiplePopularCommentInfoDto> fromPopularCommentWithOutIsLike(List<PopularCommentWithoutIsLikeDto> dtos) {
        return dtos.stream()
                .map(MultiplePopularCommentInfoDto::from)
                .toList();
    }

    public static FindCommentAllByLikedConditionDto ofLiked(Long accountId, Long lastCommentId, Pageable pageable) {
        return new FindCommentAllByLikedConditionDto(accountId, lastCommentId, pageable);
    }

    public static FindCommentAllByWrittenConditionDto ofWritten(Long accountId, Long lastCommentId, Pageable pageable) {
        return new FindCommentAllByWrittenConditionDto(accountId, lastCommentId, pageable);
    }

    public static List<LikedCommentDto> ofLiked(List<Comment> comments, Account account) {
        return comments.stream()
                       .map(comment -> LikedCommentDto.of(account, comment))
                       .toList();
    }

    public static List<WrittenCommentDto> ofWritten(List<Comment> comments, Account account) {
        return comments.stream()
                       .map(comment -> WrittenCommentDto.of(account, comment))
                       .toList();
    }

    public static ReadCommentAllByLikedDto ofLiked(String email, Long lastCommentId, Pageable pageable) {
        return new ReadCommentAllByLikedDto(email, lastCommentId, pageable);
    }

    public static ReadCommentAllByWrittenDto ofWritten(String email, Long lastCommentId, Pageable pageable) {
        return new ReadCommentAllByWrittenDto(email, lastCommentId, pageable);
    }

    public static List<CommentInfoWithLikeDto> toNonMemberCommentList(List<CommentInfoWithLikeDto> comments) {
        return comments.stream()
                .map(comment -> new CommentInfoWithLikeDto(
                        comment.commentId(),
                        comment.writerId(),
                        comment.wordId(),
                        comment.content(),
                        comment.likeCount(),
                        comment.createdAt(),
                        comment.updatedAt(),
                        comment.writerNickname(),
                        comment.writerProfileImage(),
                        comment.likeAccountId(),
                        false
                ))
                .toList();
    }

}
