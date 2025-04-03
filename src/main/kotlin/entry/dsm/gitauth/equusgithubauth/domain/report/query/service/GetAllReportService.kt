package entry.dsm.gitauth.equusgithubauth.domain.report.query.service

import entry.dsm.gitauth.equusgithubauth.domain.report.query.dto.response.ReportAllQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.report.query.repository.ReportQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllReportService(
    private val reportQueryRepository: ReportQueryRepository,
) {
    @Transactional(readOnly = true)
    fun getAllReports(): List<ReportAllQueryResponse> {
        return reportQueryRepository.findAll().map { ReportAllQueryResponse.from(it) }
    }
}
