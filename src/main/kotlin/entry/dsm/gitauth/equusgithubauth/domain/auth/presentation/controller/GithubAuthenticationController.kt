package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.GithubUserInformation
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.GithubUserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/github/auth")
class GithubAuthenticationController(
    val githubUserService: GithubUserService
) {
    @GetMapping("/authenticated/")
    fun getGithubUserInfo(@AuthenticationPrincipal oAuth2User: OAuth2User): GithubUserInformation {
        return githubUserService.getGithubUserInformation(oAuth2User)
    }

    @GetMapping("/not/authenticated/")
    fun notAuthenticated(): String {
        return "Not authenticated"
    }
}