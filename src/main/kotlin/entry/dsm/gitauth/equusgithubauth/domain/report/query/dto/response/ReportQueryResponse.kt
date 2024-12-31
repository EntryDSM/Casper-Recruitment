package entry.dsm.gitauth.equusgithubauth.domain.report.query.dto.response

import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.Major
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.ProgrammingExperience

data class ReportQueryResponse(
    val reportId: Long,
    val applicantName: String,
    val studentId: String,
    val phoneNumber: String,
    val programmingExperience: ProgrammingExperience,
    val major: Major,
    val motivation: String,
    val selfIntroduction: String
) {
    companion object {
        fun from(report: Report): ReportQueryResponse {
            return ReportQueryResponse(
                reportId = report.reportId,
                applicantName = report.applicantName,
                studentId = report.studentId,
                phoneNumber = report.phoneNumber,
                programmingExperience = report.programmingExperience,
                major = report.major,
                motivation = report.motivation,
                selfIntroduction = report.selfIntroduction
            )
        }
    }
}