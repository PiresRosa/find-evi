package com.dnd.spaced.domain.report.domain.repository;

import com.dnd.spaced.domain.report.domain.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportCrudRepository extends CrudRepository<Report, Long> {
}
