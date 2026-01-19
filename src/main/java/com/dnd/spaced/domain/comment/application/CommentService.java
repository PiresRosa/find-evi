package com.dnd.spaced.domain.comment.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.comment.application.dto.CommentServiceMapper;
import com.dnd.spaced.domain.comment.application.dto.request.CommentConditionInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.CreateCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.LikeInfoDto;
import com.dnd.spaced.domain.comment.application.dto.request.UpdateCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.application.exception.CommentNotFoundException;
import com.dnd.spaced.domain.comment.application.exception.CommentWordNotFoundException;
import com.dnd.spaced.domain.comment.application.exception.ForbiddenDeleteCommentException;
import com.dnd.spaced.domain.comment.application.exception.ForbiddenLikeException;
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
        Account writer = accountRepository.findBy(dto.email())
                                          .orElseThrow(UnauthorizedCommentException::new);
        Word word = wordRepository.findBy(dto.wordId())
                                  .orElseThrow(CommentWordNotFoundException::new);
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
        Account writer = accountRepository.findBy(dto.email())
                                          .orElseThrow(UnauthorizedCommentException::new);
        Comment comment = commentRepository.findBy(dto.commentId())
                                           .orElseThrow(CommentWordNotFoundException::new);

        if (comment.isOwner(writer.getId())) {
            throw new ForbiddenUpdateCommentException();
        }

        comment.changeContent(dto.content());
    }

    @Transactional
    public void delete(String email, Long commentId) {
        Account writer = accountRepository.findBy(email)
                                          .orElseThrow(UnauthorizedCommentException::new);
        Comment comment = commentRepository.findBy(commentId)
                                           .orElseThrow(CommentNotFoundException::new);

        if (comment.isOwner(writer.getId())) {
            throw new ForbiddenDeleteCommentException();
        }

        commentRepository.delete(comment);

        Word word = wordRepository.findBy(comment.getWordId())
                                  .orElseThrow(CommentWordNotFoundException::new);

        word.deleteComment();
    }

    @Transactional
    public void processLike(LikeInfoDto dto) {
        Account account = accountRepository.findBy(dto.email())
                                           .orElseThrow(ForbiddenLikeException::new);
        Comment comment = commentRepository.findBy(dto.commentId())
                                           .orElseThrow(CommentNotFoundException::new);
        Word word = wordRepository.findBy(dto.wordId())
                                  .orElseThrow(CommentWordNotFoundException::new);

        likeRepository.findBy(account.getId(), comment.getWordId())
                      .ifPresentOrElse(
                              like -> {
                                  likeRepository.delete(like);
                                  word.deleteComment();
                              },
                              () -> {
                                  Like like = Like.builder()
                                                  .accountId(account.getId())
                                                  .commentId(comment.getId())
                                                  .build();

                                  likeRepository.save(like);
                                  word.addComment();
                              }
                      );
    }

    public List<MultipleCommentInfoDto> findAllBy(CommentConditionInfoDto dto) {
        Long accountId = findAccountId(dto.email());
        CommentConditionDto commentConditionDto = CommentRepositoryMapper.to(
                dto.lastCommentId(),
                dto.lastLikeCount(),
                dto.pageable()
        );
        List<CommentInfoWithLikeDto> result = commentRepository.findAllBy(commentConditionDto, accountId);

        return CommentServiceMapper.fromComment(result);
    }

    public List<MultiplePopularCommentInfoDto> findPopularAllBy(Pageable pageable, String email) {
        Long accountId = findAccountId(email);
        List<PopularCommentInfoDto> result = commentRepository.findPopularAllBy(pageable, accountId);

        return CommentServiceMapper.fromPopularComment(result);
    }

    private Long findAccountId(String email) {
        return accountRepository.findBy(email)
                                .map(Account::getId)
                                .orElse(UNAUTHORIZED_ACCOUNT_ID);
    }
}
