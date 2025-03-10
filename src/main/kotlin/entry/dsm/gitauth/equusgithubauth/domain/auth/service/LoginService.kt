package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.PasswordMissMatchException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.LoginRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.userFacade.UserFacade
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userFacade: UserFacade,
) {
    @Transactional
    fun execute(request: LoginRequest): TokenResponse {
        val user = userFacade.findByUserName(request.userName)

        if (passwordEncoder.matches(user.password, request.password)) {
            throw PasswordMissMatchException
        }

        return jwtTokenProvider.generateToken(user.userName)
    }
}
