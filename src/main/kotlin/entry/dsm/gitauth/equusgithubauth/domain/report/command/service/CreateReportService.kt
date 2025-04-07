package entry.dsm.gitauth.equusgithubauth.domain.report.command.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.repository.NoticeRepository
import entry.dsm.gitauth.equusgithubauth.domain.notice.exception.NoticeNotFoundException
import entry.dsm.gitauth.equusgithubauth.domain.report.command.dto.request.CreateReportCommand
import entry.dsm.gitauth.equusgithubauth.domain.report.command.repository.ReportRepository
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateReportService(
    private val reportRepository: ReportRepository,
    private val noticeRepository: NoticeRepository,
) {
    @Transactional
    fun submitReport(command: CreateReportCommand): Report {
        // Notice 엔티티 가져오기
        val notice =
            noticeRepository.findById(command.noticeId)
                .orElseThrow { NoticeNotFoundException() }

        // Report 엔티티 생성
        val report =
            Report(
                applicantName = command.applicantName,
                studentId = command.studentId,
                phoneNumber = command.phoneNumber,
                programmingExperience = command.programmingExperience,
                major = command.major,
                motivation = command.motivation,
                selfIntroduction = command.selfIntroduction,
                notice = notice,
            )

        return reportRepository.save(report)
    }
}
