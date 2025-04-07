package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.GithubOauthService
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAuthorizationCodeException
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubAuthProperties
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/github/auth")
class GithubOauthController(
    private val gitHubOauthService: GithubOauthService,
    private val githubAuthProperties: GithubAuthProperties,
) {
    @GetMapping("/authentication")
    fun githubAuth(): RedirectView {
        return RedirectView(githubAuthProperties.redirectUrl)
    }

    @GetMapping("/login/oauth2/code/github")
    fun githubCallback(
        @RequestParam("code") code: String,
        response: HttpServletResponse,
    ) {
        if (code.isBlank()) {
            throw InvalidAuthorizationCodeException()
        }
        gitHubOauthService.execute(code, response)
    }

    @GetMapping("/not/authentication")
    fun githubLoginFailure(): String {
        return githubAuthProperties.failure
    }
}
