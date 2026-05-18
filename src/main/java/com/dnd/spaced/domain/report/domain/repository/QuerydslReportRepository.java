package com.dnd.spaced.domain.report.domain.repository;

import com.dnd.spaced.domain.report.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslReportRepository implements ReportRepository {

    private final ReportCrudRepository reportCrudRepository;

    @Override
    public void save(Report report) {
        reportCrudRepository.save(report);
    }
}
