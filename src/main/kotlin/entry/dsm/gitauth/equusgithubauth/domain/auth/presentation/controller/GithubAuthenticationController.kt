package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubAuthProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.GenerateGithubTokenService
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAccessTokenException
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAuthorizationCodeException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.response.GithubAccessTokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.service.UserService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
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
    private val messageSource: MessageSource
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

    @GetMapping("/authenticated")
    fun githubLoginSuccess(
        @RequestHeader("Authorization") accessToken: String,
    ): LoginSuccessResponse {
        if (accessToken.isBlank()) {
            throw InvalidAccessTokenException()
        }
        val token = accessToken.trim().let {
            if (it.startsWith("Bearer ")) it.substring(7) else it
        }
        if (token.isBlank()) {
            throw InvalidAccessTokenException()
        }
        return userService.execute(token)
    }

    @GetMapping("/not/authenticated")
    fun githubLoginFailure(): String {
        return messageSource.getMessage(
            "githubAuthProperties.failure",
            null,
            LocaleContextHolder.getLocale()
        )
    }
}