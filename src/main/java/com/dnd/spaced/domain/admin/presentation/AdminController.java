package com.dnd.spaced.domain.admin.presentation;

import com.dnd.spaced.domain.admin.application.AdminService;
import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.admin.presentation.dto.response.ReportListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/words")
    public ResponseEntity<Void> createWord(Authentication authentication,
                                           @Valid @RequestBody AdminWordRequestDto wordRequestDto) {
        Long wordId = adminService.createWord(wordRequestDto);
        return ResponseEntity.created(URI.create("/words/" + wordId)).build();
    }

    @DeleteMapping("/words/{id}")
    public ResponseEntity<Void> deleteWord(Authentication authentication,
                                           @PathVariable Long id) {
        adminService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/words/{id}")
    public ResponseEntity<Void> updateWord(Authentication authentication,
                                           @PathVariable Long id,
                                           @Valid @RequestBody AdminWordRequestDto wordRequestDto) {
        adminService.updateWord(id, wordRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/words/{id}")
    public ResponseEntity<AdminWordResponse> getWord(@PathVariable Long id) {
        AdminWordResponse wordResponseDto = adminService.getWord(id);
        return ResponseEntity.ok(wordResponseDto);
    }

    @PostMapping("/reports/{id}/accept")
    public ResponseEntity<Void> acceptReport(@PathVariable Long id) {
        adminService.acceptReport(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reports/{id}/ignore")
    public ResponseEntity<Void> ignoreReport(@PathVariable Long id) {
        adminService.ignoreReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports")
    public ResponseEntity<ReportListResponse> findReports(@RequestParam(required = false) Long lastReportId) {
        List<ReportInfoDto> reports = adminService.findReports(lastReportId);

        Long newLastReportId = reports.isEmpty() ? lastReportId : reports.get(reports.size() - 1).id();

        ReportListResponse response = new ReportListResponse(reports, newLastReportId);
        return ResponseEntity.ok(response);
    }
}
