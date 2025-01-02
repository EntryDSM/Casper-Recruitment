package entry.dsm.gitauth.equusgithubauth.domain.user.exception

import entry.dsm.gitauth.equusgithubauth.global.error.exception.BusinessException
import entry.dsm.gitauth.equusgithubauth.global.error.exception.ErrorCode

object InvalidTokenException: BusinessException(ErrorCode.INVALID_TOKEN)