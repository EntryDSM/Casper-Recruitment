package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.RefreshToken
import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.RefreshTokenNotFoundException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.TokenRefreshRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtProperties
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProperties: JwtProperties
) {
    @Transactional
    fun execute(
        request: TokenRefreshRequest
    ): TokenResponse {
        val refreshToken: RefreshToken = refreshTokenRepository.findByToken(request.accessToken)
            ?: throw RefreshTokenNotFoundException

        val username = refreshToken.username
        val tokens = jwtTokenProvider.generateToken(username)

        refreshToken.updateToken(tokens.refreshToken, jwtProperties.refreshExp)

        return tokens
    }
}