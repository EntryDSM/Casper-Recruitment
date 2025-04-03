package entry.dsm.gitauth.equusgithubauth.domain.report.query.dto.response

import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report

data class ReportAllQueryResponse(
    val reportId: Long,
    val applicantName: String,
    val studentId: String
) {
    companion object {
        fun from(report: Report): ReportAllQueryResponse {
            return ReportAllQueryResponse(
                reportId = report.reportId,
                applicantName = report.applicantName,
                studentId = report.studentId
            )
        }
    }
}
