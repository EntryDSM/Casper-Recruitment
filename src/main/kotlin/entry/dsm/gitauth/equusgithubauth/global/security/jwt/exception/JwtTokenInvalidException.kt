package entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class JwtTokenInvalidException : CustomException(ErrorCode.JWT_TOKEN_INVALID)
