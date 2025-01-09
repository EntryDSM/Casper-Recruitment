package entry.dsm.gitauth.equusgithubauth.domain.report.query.service

import entry.dsm.gitauth.equusgithubauth.domain.report.exception.ReportNotFoundException
import entry.dsm.gitauth.equusgithubauth.domain.report.query.dto.response.ReportQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.report.query.repository.ReportQueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportQueryService(
    private val reportQueryRepository: ReportQueryRepository,
) {
    @Transactional(readOnly = true)
    fun getReport(reportId: Long): ReportQueryResponse {
        val report =
            reportQueryRepository.findByIdOrNull(reportId)
                ?: throw ReportNotFoundException
        return ReportQueryResponse.from(report)
    }
}
