package entry.dsm.gitauth.equusgithubauth.domain.report.query.service

import entry.dsm.gitauth.equusgithubauth.domain.report.query.dto.response.ReportQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.report.query.repository.ReportQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllReportService(
    private val reportQueryRepository: ReportQueryRepository
) {
    @Transactional(readOnly = true)
    fun getAllReports(): List<ReportQueryResponse> {
        return reportQueryRepository.findAll().map { ReportQueryResponse.from(it) }
    }
}
