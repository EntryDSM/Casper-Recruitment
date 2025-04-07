package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.LogoutService
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.OauthReissueService
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.LogoutRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.RefreshTokenRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/google/auth")
class GoogleOauthController(
    @Value("\${oauth.google.redirect}") private val googleRedirectUrl: String,
    private val oauthReissueService: OauthReissueService,
    private val logoutService: LogoutService
) {
    @GetMapping
    fun googleAuth(): RedirectView {
        return RedirectView(googleRedirectUrl)
    }

    @PatchMapping("/reissue")
    fun reissueToken(
        @RequestBody @Valid request: RefreshTokenRequest,
    ): TokenResponse {
        return oauthReissueService.reissue(request.refreshToken)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody @Valid request: LogoutRequest,
    ) {
        logoutService.logout(request.accessToken)
    }
}
