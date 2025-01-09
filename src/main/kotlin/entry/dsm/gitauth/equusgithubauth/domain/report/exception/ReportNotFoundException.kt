package entry.dsm.gitauth.equusgithubauth.domain.report.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

object ReportNotFoundException : CustomException(ErrorCode.REPORT_NOT_FOUND)
