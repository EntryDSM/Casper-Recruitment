package entry.dsm.gitauth.equusgithubauth.domain.auth.service


import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception.JwtTokenInvalidException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.repository.findByIdOrNull

@Service
class LogoutService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenBlackListService: TokenBlackListService
) {

    @Transactional
    fun logout(accessToken: String) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw JwtTokenInvalidException()
        }

        val userName = jwtTokenProvider.getSubjectFromToken(accessToken)

        refreshTokenRepository.findByIdOrNull(userName)?.let {
            refreshTokenRepository.delete(it)
        }

        tokenBlackListService.blackList(accessToken)
    }
}

