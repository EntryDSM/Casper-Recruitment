package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.auth.exception.RefreshTokenNotFoundException
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class OauthReissueService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun reissue(refreshToken: String): TokenResponse {
        val storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
            ?: throw RefreshTokenNotFoundException()

        return jwtTokenProvider.reissueToken(storedToken)
    }
}
