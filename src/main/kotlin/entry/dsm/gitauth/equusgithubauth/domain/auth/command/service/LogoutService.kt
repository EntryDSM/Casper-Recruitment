package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    @Transactional
    fun logout(accessToken: String) {
        jwtTokenProvider.validateToken(accessToken)

        val userName = jwtTokenProvider.getSubjectFromToken(accessToken)

        refreshTokenRepository.findByIdOrNull(userName)?.let {
            refreshTokenRepository.delete(it)
        }
    }
}
