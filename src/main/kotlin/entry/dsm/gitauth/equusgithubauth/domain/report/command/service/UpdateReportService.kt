package entry.dsm.gitauth.equusgithubauth.domain.report.command.service

import entry.dsm.gitauth.equusgithubauth.domain.report.command.dto.request.UpdateReportCommand
import entry.dsm.gitauth.equusgithubauth.domain.report.command.repository.ReportRepository
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import entry.dsm.gitauth.equusgithubauth.domain.report.exception.ReportNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateReportService(
    private val reportRepository: ReportRepository,
) {
    @Transactional
    fun updateReport(
        reportId: Long,
        command: UpdateReportCommand,
    ): Report {
        val report =
            reportRepository.findByIdOrNull(reportId)
                ?: throw ReportNotFoundException

        report.apply {
            applicantName = command.applicantName
            studentId = command.studentId
            phoneNumber = command.phoneNumber
            programmingExperience = command.programmingExperience
            major = command.major
            motivation = command.motivation
            selfIntroduction = command.selfIntroduction
        }

        return reportRepository.save(report)
    }
}
