package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller


import entry.dsm.gitauth.equusgithubauth.domain.auth.service.LogoutService
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.OauthReissueService
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.LogoutRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.request.RefreshTokenRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/google/auth")
class Oauth2Controller(
    private val jwtTokenProvider: JwtTokenProvider,
    private val logoutService: LogoutService,
    private val oauthReissueService: OauthReissueService
) {

    @GetMapping
    fun googleAuth(): String {
        return "redirect:/oauth2/authorization/google"
    }

    @PostMapping("/reissue")
    fun reissueToken(@RequestBody request: RefreshTokenRequest): TokenResponse {
        return oauthReissueService.reissue(request.refreshToken)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: LogoutRequest) {
        logoutService.logout(request.accessToken, request.refreshToken)
    }



}