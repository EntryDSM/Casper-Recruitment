package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.service.LogoutService
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.OauthReissueService
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.LogoutRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.RefreshTokenRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/google/auth")
class Oauth2Controller(
    @Value("\${oauth.google.redirect}") private val googleRedirectUrl: String,
    private val logoutService: LogoutService,
    private val oauthReissueService: OauthReissueService
) {

    @GetMapping
    fun googleAuth(): RedirectView {
        return RedirectView(googleRedirectUrl)
    }

    @PatchMapping("/reissue")
    fun reissueToken(@RequestBody request: RefreshTokenRequest): TokenResponse {
        return oauthReissueService.reissue(request.refreshToken)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: LogoutRequest) {
        logoutService.logout(request.accessToken)
    }
}
