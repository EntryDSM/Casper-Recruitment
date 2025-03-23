package entry.dsm.gitauth.equusgithubauth.domain.auth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

object InvalidAccessToken : CustomException(ErrorCode.INVALID_ACCESS_TOKEN)