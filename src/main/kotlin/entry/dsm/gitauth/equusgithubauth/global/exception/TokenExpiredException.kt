package entry.dsm.gitauth.equusgithubauth.global.exception

import entry.dsm.gitauth.equusgithubauth.global.error.exception.BusinessException
import entry.dsm.gitauth.equusgithubauth.global.error.exception.ErrorCode

object TokenExpiredException : BusinessException(ErrorCode.USER_NOT_FOUND)
