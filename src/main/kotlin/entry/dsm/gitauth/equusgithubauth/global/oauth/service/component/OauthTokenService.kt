package entry.dsm.gitauth.equusgithubauth.global.oauth.service.component


import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Component

@Component
class OauthTokenService(
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun generateTokenResponse(loginId: String): TokenResponse {
        return jwtTokenProvider.generateToken(loginId)
    }
}