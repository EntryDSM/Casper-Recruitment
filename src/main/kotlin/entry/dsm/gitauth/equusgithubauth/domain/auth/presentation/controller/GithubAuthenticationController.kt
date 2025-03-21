package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.response.GithubAccessTokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.GenerateGithubTokenService
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
    private val userService: UserService
) {
    // redirect
    @GetMapping
    fun githubAuth(): RedirectView {
        return RedirectView("/oauth2/authorization/github")
    }

    // code to token
    @GetMapping("/login/oauth2/code/github")
    fun githubCallback(
        @RequestParam("code") code: String
    ): GithubAccessTokenResponse {
        return generateGithubTokenService.execute(code)
    }

    @GetMapping("/authenticated")
    fun githubLoginSuccess(@RequestHeader("Authorization") accessToken: String): LoginSuccessResponse {
        return userService.execute(accessToken)
    }

    @GetMapping("/not/authenticated")
    fun githubLoginFailure(): String {
        return "Not authenticated"
    }
}
