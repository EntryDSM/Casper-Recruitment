package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.LogoutService
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.OauthReissueService
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.LogoutRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.RefreshTokenRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val logoutService: LogoutService,
    private val oauthReissueService: OauthReissueService,
) {
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
