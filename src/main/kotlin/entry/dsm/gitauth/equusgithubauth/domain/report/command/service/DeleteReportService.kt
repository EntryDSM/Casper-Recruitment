package entry.dsm.gitauth.equusgithubauth.domain.report.command.service

import entry.dsm.gitauth.equusgithubauth.domain.report.command.repository.ReportRepository
import entry.dsm.gitauth.equusgithubauth.domain.report.exception.ReportNotFoundException
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
                ?: throw ReportNotFoundException
        reportRepository.delete(report)
    }
}
