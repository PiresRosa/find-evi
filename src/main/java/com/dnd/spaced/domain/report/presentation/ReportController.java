package com.dnd.spaced.domain.report.presentation;

import com.dnd.spaced.domain.report.application.ReportService;
import com.dnd.spaced.domain.report.application.ReportServiceMapper;
import com.dnd.spaced.domain.report.presentation.dto.request.ReportInfoRequest;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController implements SwaggerReportController {

    private final ReportService reportService;

    @PostMapping("/comments/{id}")
    public ResponseEntity<Void> save(
            @AuthAccount AuthAccountInfo accountInfo,
            @Valid @RequestBody ReportInfoRequest request,
            @PathVariable Long id
    ) {
        reportService.save(ReportServiceMapper.of(accountInfo.email(), request.reasonName(), id));

        return ResponseEntityConst.NO_CONTENT;
    }
}
