package site.archive.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.archive.domain.common.BaseTimeEntity;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public boolean isReportedBy(Long archiveId, Long userId) {
        return reportRepository.findByArchiveIdAndUserId(archiveId, userId)
                               .filter(report -> !report.getIsDeleted())
                               .isPresent();
    }

    @Transactional
    public void reportArchive(Long archiveId, Long userId, String reason) {
        var report = reportRepository.findByArchiveIdAndUserId(archiveId, userId)
                                     .orElseGet(() -> Report.of(reason, archiveId, userId));
        report.softDeleteCancel();
        reportRepository.save(report);
    }

    @Transactional
    public void cancelReportArchive(Long archiveId, Long userId) {
        reportRepository.findByArchiveIdAndUserId(archiveId, userId)
                        .ifPresent(BaseTimeEntity::softDelete);
    }

}
