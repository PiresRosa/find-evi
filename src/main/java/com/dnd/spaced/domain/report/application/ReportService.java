package com.dnd.spaced.domain.report.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.comment.domain.repository.CommentRepository;
import com.dnd.spaced.domain.report.application.dto.request.ReportInfoDto;
import com.dnd.spaced.domain.report.application.exception.ForbiddenReportAccountException;
import com.dnd.spaced.domain.report.application.exception.ReportedCommentNotFoundException;
import com.dnd.spaced.domain.report.domain.Report;
import com.dnd.spaced.domain.report.domain.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    public void save(ReportInfoDto dto) {
        Account reporter = accountRepository.findBy(dto.email())
                                            .orElseThrow(ForbiddenReportAccountException::new);

        if (commentRepository.existsBy(dto.commentId())) {
            throw new ReportedCommentNotFoundException();
        }

        Report report = Report.builder()
                             .commentId(dto.commentId())
                             .reporterId(reporter.getId())
                             .reasonName(dto.reasonName())
                             .build();

        reportRepository.save(report);
    }
}
