package entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

object JwtTokenExpiredException : CustomException(ErrorCode.JWT_TOKEN_EXPIRED)
