package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubAuthProperties
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class GithubAuthenticationFailureHandler(
    private val githubAuthProperties: GithubAuthProperties,
) : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        response.sendRedirect(githubAuthProperties.failureAuthenticationUrl)
    }
}
