package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.GitHubOauthService
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAuthorizationCodeException
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubAuthProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/github/auth")
class GithubAuthenticationController(
    private val gitHubOauthService: GitHubOauthService,
    private val githubAuthProperties: GithubAuthProperties,
) {
    @GetMapping("/authentication")
    fun githubAuth(): RedirectView {
        return RedirectView(githubAuthProperties.redirectUrl)
    }

    @GetMapping("/login/oauth2/code/github")
    fun githubCallback(
        @RequestParam("code") code: String,
    ): TokenResponse {
        if (code.isBlank()) {
            throw InvalidAuthorizationCodeException()
        }
        return gitHubOauthService.execute(code)
    }

    @GetMapping("/not/authentication")
    fun githubLoginFailure(): String {
        return githubAuthProperties.failure
    }
}
