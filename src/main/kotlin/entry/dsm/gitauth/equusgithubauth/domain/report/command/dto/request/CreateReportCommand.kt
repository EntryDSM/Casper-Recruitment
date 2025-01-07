package entry.dsm.gitauth.equusgithubauth.domain.report.command.dto.request

import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.Major
import entry.dsm.gitauth.equusgithubauth.domain.report.entity.enums.ProgrammingExperience

data class CreateReportCommand(
    val noticeId: Long,
    val applicantName: String,
    val studentId: String,
    val phoneNumber: String,
    val programmingExperience: ProgrammingExperience,
    val major: Major,
    val motivation: String,
    val selfIntroduction: String,
)
