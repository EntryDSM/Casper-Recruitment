package entry.dsm.gitauth.equusgithubauth.global.exception

import org.springframework.security.core.AuthenticationException

class CustomOauth2AuthenticationException(
    message: String?,
    cause: Throwable?
) : AuthenticationException(message, cause)