package entry.dsm.gitauth.equusgithubauth.domain.report.command.repository

import entry.dsm.gitauth.equusgithubauth.domain.report.entity.Report
import org.springframework.data.repository.CrudRepository

interface ReportRepository : CrudRepository<Report, Long>
