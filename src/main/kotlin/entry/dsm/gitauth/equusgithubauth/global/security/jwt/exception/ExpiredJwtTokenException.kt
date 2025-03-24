package entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode
<<<<<<< HEAD:src/main/kotlin/entry/dsm/gitauth/equusgithubauth/global/security/jwt/exception/JwtTokenExpiredException.kt
class JwtTokenExpiredException : CustomException(ErrorCode.JWT_TOKEN_EXPIRED)
=======

class ExpiredJwtTokenException : CustomException(ErrorCode.JWT_TOKEN_EXPIRED)
>>>>>>> origin/feature/#35-github-oauth:src/main/kotlin/entry/dsm/gitauth/equusgithubauth/global/security/jwt/exception/ExpiredJwtTokenException.kt
