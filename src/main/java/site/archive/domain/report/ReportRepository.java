package site.archive.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.report.entity.Report;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByArchiveIdAndUserId(Long archiveId, Long userId);

}
