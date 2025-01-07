package entry.dsm.gitauth.equusgithubauth.domain.report.presentation

import entry.dsm.gitauth.equusgithubauth.domain.report.command.dto.request.CreateReportCommand
import entry.dsm.gitauth.equusgithubauth.domain.report.command.dto.request.UpdateReportCommand
import entry.dsm.gitauth.equusgithubauth.domain.report.command.service.CreateReportService
import entry.dsm.gitauth.equusgithubauth.domain.report.command.service.DeleteReportService
import entry.dsm.gitauth.equusgithubauth.domain.report.command.service.UpdateReportService
import entry.dsm.gitauth.equusgithubauth.domain.report.query.dto.response.ReportQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.report.query.service.GetAllReportService
import entry.dsm.gitauth.equusgithubauth.domain.report.query.service.ReportQueryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class ReportController(
    private val createReportService: CreateReportService,
    private val updateReportService: UpdateReportService,
    private val deleteReportService: DeleteReportService,
    private val getAllReportService: GetAllReportService,
    private val reportQueryService: ReportQueryService,
) {
    // Create a new report
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createReport(
        @RequestBody command: CreateReportCommand,
    ) {
        createReportService.submitReport(command)
    }

    // Retrieve a single report by ID
    @GetMapping("/{id}")
    fun getReport(
        @PathVariable id: Long,
    ): ReportQueryResponse {
        return reportQueryService.getReport(id)
    }

    // Retrieve all reports
    @GetMapping
    fun getAllReports(): List<ReportQueryResponse> {
        return getAllReportService.getAllReports()
    }

    // Update an existing report
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateReport(
        @PathVariable id: Long,
        @RequestBody command: UpdateReportCommand,
    ) {
        updateReportService.updateReport(id, command)
    }

    // Delete a report by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReport(
        @PathVariable id: Long,
    ) {
        deleteReportService.deleteReport(id)
    }
}
