package entry.dsm.gitauth.equusgithubauth.domain.report.command.service

import entry.dsm.gitauth.equusgithubauth.domain.report.command.repository.ReportRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteReportService(
    private val reportRepository: ReportRepository,
) {
    @Transactional
    fun deleteReport(reportId: Long) {
        val report =
            reportRepository.findByIdOrNull(reportId)
                ?: throw IllegalArgumentException("Report with id $reportId not found")

        reportRepository.delete(report)
    }
}
