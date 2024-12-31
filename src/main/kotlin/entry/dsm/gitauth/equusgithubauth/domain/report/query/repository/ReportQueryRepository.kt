package entry.dsm.gitauth.equusgithubauth.domain.report.query.repository

import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import org.springframework.data.repository.CrudRepository

interface ReportQueryRepository : CrudRepository<Report, Long> {
}