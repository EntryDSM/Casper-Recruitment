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
        val redisRefreshToken: RefreshToken =
            refreshTokenRepository.findByRefreshToken(refreshToken)
                ?: throw RefreshTokenNotFoundException

        val userName = redisRefreshToken.userName
        val tokens = jwtTokenProvider.generateToken(userName)

        redisRefreshToken.updateToken(tokens.refreshToken, jwtProperties.refreshExp)

        return tokens
    }
}
