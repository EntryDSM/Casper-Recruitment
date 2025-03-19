package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.CreateGithubTokenService
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/github")
class GithubAuthenticationController(
    val createGithubTokenService: CreateGithubTokenService
) {
    // redirect
    @GetMapping
    fun githubAuth(): RedirectView {
        return RedirectView("/oauth2/authorization/github")
    }

    // code to token
    @GetMapping("/callback")
    fun generateGithubToken(
        @RequestParam code: String
    ): TokenResponse {
        return createGithubTokenService.execute(code)
    }
}
