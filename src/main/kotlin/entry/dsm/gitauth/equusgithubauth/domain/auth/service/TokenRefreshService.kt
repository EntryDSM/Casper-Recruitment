package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.RefreshToken
import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.RefreshTokenNotFoundException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtProperties
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProperties: JwtProperties,
) {
    @Transactional
    fun execute(refreshToken: String): TokenResponse {
        val rfToken: RefreshToken =
            refreshTokenRepository.findByRefreshToken(refreshToken)
                ?: throw RefreshTokenNotFoundException

        val username = rfToken.username
        val tokens = jwtTokenProvider.generateToken(username)

        rfToken.updateToken(tokens.refreshToken, jwtProperties.refreshExp)

        return tokens
    }
}
