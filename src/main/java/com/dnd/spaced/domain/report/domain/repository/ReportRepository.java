package com.dnd.spaced.domain.report.domain.repository;

import com.dnd.spaced.domain.report.domain.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    void save(Report report);

    Optional<Report> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Report> findReportsAfterId(Long lastReportId, int size);
}
