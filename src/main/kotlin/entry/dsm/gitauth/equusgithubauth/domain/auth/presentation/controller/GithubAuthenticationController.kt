package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/github/auth")
class GithubAuthenticationController(
    val userService: UserService,
) {
    @GetMapping
    fun githubAuth(): RedirectView {
        return RedirectView("/oauth2/authorization/github")
    }

    @GetMapping("/authenticated/")
    fun getGithubUserInfo(
        @AuthenticationPrincipal oAuth2User: OAuth2User,
    ): TokenResponse {
        return userService.execute(oAuth2User)
    }

    @GetMapping("/not/authenticated/")
    fun notAuthenticated(): String {
        return "Not authenticated"
    }
}
