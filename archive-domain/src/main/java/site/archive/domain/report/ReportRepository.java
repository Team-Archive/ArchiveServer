package site.archive.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByArchiveIdAndUserId(Long archiveId, Long userId);

}
