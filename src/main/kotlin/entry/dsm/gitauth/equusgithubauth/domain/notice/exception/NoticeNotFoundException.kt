package entry.dsm.gitauth.equusgithubauth.domain.notice.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

object NoticeNotFoundException : CustomException(ErrorCode.NOTICE_NOT_FOUND)
