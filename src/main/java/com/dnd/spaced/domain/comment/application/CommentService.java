package com.dnd.spaced.domain.comment.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.comment.application.dto.CommentServiceMapper;
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
import com.dnd.spaced.domain.comment.application.exception.CommentNotFoundException;
import com.dnd.spaced.domain.comment.application.exception.CommentWordNotFoundException;
import com.dnd.spaced.domain.comment.application.exception.ForbiddenDeleteCommentException;
import com.dnd.spaced.domain.comment.application.exception.ForbiddenUpdateCommentException;
import com.dnd.spaced.domain.comment.application.exception.UnauthorizedCommentException;
import com.dnd.spaced.domain.comment.domain.Comment;
import com.dnd.spaced.domain.comment.domain.Like;
import com.dnd.spaced.domain.comment.domain.repository.CommentRepository;
import com.dnd.spaced.domain.comment.domain.repository.LikeRepository;
import com.dnd.spaced.domain.comment.domain.repository.dto.CommentRepositoryMapper;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.CommentConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentWithoutIsLikeDto;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private static final long UNAUTHORIZED_ACCOUNT_ID = -1L;

    private final WordRepository wordRepository;
    private final LikeRepository likeRepository;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void save(CreateCommentInfoDto dto) {
        Account writer = findAccount(dto.email());
        Word word = findWord(dto.wordId());
        Comment comment = Comment.builder()
                                 .accountId(writer.getId())
                                 .wordId(word.getId())
                                 .content(dto.content())
                                 .build();

        word.addComment();
        commentRepository.save(comment);
    }

    @Transactional
    public void update(UpdateCommentInfoDto dto) {
        Account writer = findAccount(dto.email());
        Comment comment = findComment(dto.commentId());

        if (comment.isOwner(writer.getId())) {
            throw new ForbiddenUpdateCommentException();
        }

        comment.changeContent(dto.content());
    }

    @Transactional
    public void delete(DeleteCommentInfoDto dto) {
        Account writer = findAccount(dto.email());
        Comment comment = findComment(dto.commentId());

        if (comment.isOwner(writer.getId())) {
            throw new ForbiddenDeleteCommentException();
        }

        commentRepository.delete(comment);

        Word word = findWord(dto.wordId());

        word.deleteComment();
    }

    @Transactional
    public void processLike(String email, Long commentId) {
        Account account = findAccount(email);
        Comment comment = findComment(commentId);

        likeRepository.findBy(account.getId(), comment.getWordId())
                      .ifPresentOrElse(
                              like -> {
                                  likeRepository.delete(like);
                                  comment.dislike();
                              },
                              () -> {
                                  Like like = Like.builder()
                                                  .accountId(account.getId())
                                                  .commentId(comment.getId())
                                                  .build();

                                  likeRepository.save(like);
                                  comment.like();
                              }
                      );
    }

    public List<MultipleCommentInfoDto> findAllBy(CommentConditionInfoDto dto) {
        CommentConditionDto commentConditionDto = CommentRepositoryMapper.to(
                dto.wordId(),
                findAccountId(dto.email()),
                dto.lastCommentId(),
                dto.lastLikeCount(),
                dto.pageable()
        );
        List<CommentInfoWithLikeDto> result = commentRepository.findAllBy(commentConditionDto);

        return CommentServiceMapper.fromComment(result);
    }

    public List<MultiplePopularCommentInfoDto> findPopularAllBy(Pageable pageable, String email) {
        if (email != null) {
            Long accountId = findAccountId(email);
            List<PopularCommentInfoDto> result = commentRepository.findPopularAllBy(pageable, accountId);

            return CommentServiceMapper.fromPopularComment(result);
        } else {
            List<PopularCommentWithoutIsLikeDto> result = commentRepository.findPopularAll(pageable);

            return CommentServiceMapper.fromPopularCommentWithOutIsLike(result);
        }
    }

    public List<LikedCommentDto> findAllByLiked(ReadCommentAllByLikedDto dto) {
        Account account = findAccount(dto.email());
        List<Comment> result = commentRepository.findAllByLiked(
                CommentServiceMapper.ofLiked(
                        account.getId(),
                        dto.lastCommentId(),
                        dto.pageable()
                )
        );

        return CommentServiceMapper.ofLiked(result, account);
    }

    public List<WrittenCommentDto> findAllByWritten(ReadCommentAllByWrittenDto dto) {
        Account account = findAccount(dto.email());
        List<Comment> result = commentRepository.findAllByWritten(
                CommentServiceMapper.ofWritten(
                        account.getId(),
                        dto.lastCommentId(),
                        dto.pageable()
                )
        );

        return CommentServiceMapper.ofWritten(result, account);
    }

    private Long findAccountId(String email) {
        return accountRepository.findBy(email)
                                .map(Account::getId)
                                .orElse(UNAUTHORIZED_ACCOUNT_ID);
    }

    private Account findAccount(String email) {
        return accountRepository.findBy(email)
                                .orElseThrow(UnauthorizedCommentException::new);
    }

    private Word findWord(Long wordId) {
        return wordRepository.findBy(wordId)
                                  .orElseThrow(CommentWordNotFoundException::new);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findBy(commentId)
                                .orElseThrow(CommentNotFoundException::new);
    }
}
