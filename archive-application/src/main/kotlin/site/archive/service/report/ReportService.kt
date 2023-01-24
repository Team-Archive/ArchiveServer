package site.archive.service.report

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.domain.common.BaseTimeEntity
import site.archive.domain.report.Report
import site.archive.domain.report.ReportRepository

@Service
@Transactional(readOnly = true)
class ReportService(private val reportRepository: ReportRepository) {

    fun isReportedBy(archiveId: Long, userId: Long): Boolean {
        return reportRepository.findByArchiveIdAndUserId(archiveId, userId)
            .filter { !it.isDeleted }
            .isPresent
    }

    @Transactional
    fun reportArchive(archiveId: Long, userId: Long, reason: String) {
        val report = reportRepository.findByArchiveIdAndUserId(archiveId, userId)
            .orElseGet { Report.of(reason, archiveId, userId) }
        report.softDeleteCancel()
        reportRepository.save(report)
    }

    @Transactional
    fun cancelReportArchive(archiveId: Long, userId: Long) {
        reportRepository.findByArchiveIdAndUserId(archiveId, userId)
            .ifPresent(BaseTimeEntity::softDelete)
    }

}