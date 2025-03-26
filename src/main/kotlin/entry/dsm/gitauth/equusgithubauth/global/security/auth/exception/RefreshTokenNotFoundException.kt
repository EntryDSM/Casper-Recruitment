package entry.dsm.gitauth.equusgithubauth.global.security.auth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class RefreshTokenNotFoundException : CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND)
