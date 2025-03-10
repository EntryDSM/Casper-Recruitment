package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.PasswordMissMatchException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.LoginRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun login(
        request: LoginRequest
    ): TokenResponse {
        val user = userRepository.findByUsername(request.username)

        if (passwordEncoder.matches(user.password, request.password)) {
            throw PasswordMissMatchException
        }

        return jwtTokenProvider.generateToken(user.username)
    }
}