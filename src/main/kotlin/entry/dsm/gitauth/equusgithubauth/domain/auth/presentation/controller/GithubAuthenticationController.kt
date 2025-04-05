package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.dto.response.GithubAccessTokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.GenerateGithubTokenService
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAccessTokenException
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAuthorizationCodeException
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.service.UserService
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubAuthProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/github/auth")
class GithubAuthenticationController(
    private val generateGithubTokenService: GenerateGithubTokenService,
    private val userService: UserService,
    private val githubAuthProperties: GithubAuthProperties,
) {
    @GetMapping
    fun githubAuth(): RedirectView {
        return RedirectView(githubAuthProperties.redirectUrl)
    }

    @GetMapping("/login/oauth2/code/github")
    fun githubCallback(
        @RequestParam("code") code: String,
    ): GithubAccessTokenResponse {
        if (code.isBlank()) {
            throw InvalidAuthorizationCodeException()
        }
        return generateGithubTokenService.execute(code)
    }

    @GetMapping("/authentication")
    fun githubLoginSuccess(
        @RequestHeader("Authorization") accessToken: String,
    ): LoginSuccessResponse {
        if (accessToken.isBlank()) {
            throw InvalidAccessTokenException()
        }
        val token =
            accessToken.trim().let {
                if (it.startsWith("Bearer ")) it.substring(7) else it
            }
        if (token.isBlank()) {
            throw InvalidAccessTokenException()
        }
        return userService.execute(token)
    }

    @GetMapping("/not/authentication")
    fun githubLoginFailure(): String {
        return githubAuthProperties.failure
    }
}
