package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun execute(refreshToken: String): TokenResponse {
        return jwtTokenProvider.reIssue(refreshToken)
    }
}
