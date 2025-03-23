package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubAuthProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.GenerateGithubTokenService
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.response.GithubAccessTokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.service.UserService
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
    private val githubAuthProperties: GithubAuthProperties
) {
    // redirect
    @GetMapping
    fun githubAuth(): RedirectView {
        return RedirectView(githubAuthProperties.redirectUrl)
    }

    // code to token
    @GetMapping("/login/oauth2/code/github")
    fun githubCallback(
        @RequestParam("code") code: String,
    ): GithubAccessTokenResponse {
        return generateGithubTokenService.execute(code)
    }

    @GetMapping("/authenticated")
    fun githubLoginSuccess(
        @RequestHeader("Authorization") accessToken: String,
    ): LoginSuccessResponse {
        val token = if (accessToken.startsWith("Bearer")) {
            accessToken.substring(7)
        } else {
            accessToken
        }
        return userService.execute(token)
    }

    @GetMapping("/not/authenticated")
    fun githubLoginFailure(): String {
        return "인증에 실패했습니다. GitHub 로그인을 다시 시도하거나 관리자에게 문의하세요."
    }
}