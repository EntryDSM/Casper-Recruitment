package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubAuthProperties
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class GithubAuthenticationSuccessHandler(
    private val githubAuthProperties: GithubAuthProperties,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        response.sendRedirect(githubAuthProperties.successAuthenticationUrl)
    }
}
