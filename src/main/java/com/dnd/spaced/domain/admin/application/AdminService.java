package com.dnd.spaced.domain.admin.application;

import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.comment.application.exception.CommentNotFoundException;
import com.dnd.spaced.domain.comment.domain.Comment;
import com.dnd.spaced.domain.comment.domain.repository.CommentRepository;
import com.dnd.spaced.domain.report.application.exception.ReportedCommentNotFoundException;
import com.dnd.spaced.domain.report.domain.Report;
import com.dnd.spaced.domain.report.domain.repository.QuerydslReportRepository;
import com.dnd.spaced.domain.report.domain.repository.ReportRepository;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final WordRepository wordRepository;
    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final QuerydslReportRepository reportQuerydslRepository;

    @Transactional
    public Long createWord(AdminWordRequestDto wordRequestDto) {
        Word word = AdminServiceMapper.fromCreateRequest(wordRequestDto);
        wordRepository.save(word);
        return word.getId();
    }

    @Transactional
    public void deleteWord(Long wordId) {
        Word word = findWordById(wordId);
        wordRepository.delete(word);
    }

    @Transactional
    public void updateWord(Long wordId, AdminWordRequestDto wordRequestDto) {
        Word word = AdminServiceMapper.fromUpdateRequest(wordRequestDto);
        wordRepository.save(word);
    }

    @Transactional
    public void acceptReport(Long reportId) {
        Report report = getReport(reportId);
        Comment comment = getComment(report.getCommentId());

        commentRepository.delete(comment);
        reportRepository.deleteById(reportId);
    }

    @Transactional
    public void ignoreReport(Long reportId) {
        Report report = getReport(reportId);
        reportRepository.deleteById(reportId);
    }

    @Transactional
    public List<ReportInfoDto> findReports(Long lastReportId) {
        int size = 15;
        List<Report> reports = reportQuerydslRepository.findReportsAfterId(lastReportId, size);

        return reports.stream()
                .map(AdminServiceMapper::toReportInfoDto)
                .collect(Collectors.toList());
    }

    public AdminWordResponse getWord(Long wordId) {
        Word word = findWordById(wordId);
        return AdminServiceMapper.toResponseDto(word);
    }

    private Report getReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(ReportedCommentNotFoundException::new);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findBy(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    private Word findWordById(Long wordId) {
        return wordRepository.findBy(wordId)
                .orElseThrow(WordNotFoundException::new);
    }
}

