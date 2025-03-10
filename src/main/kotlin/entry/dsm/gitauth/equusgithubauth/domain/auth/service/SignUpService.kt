package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.SignUpRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SignUpService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun execute(
        request: SignUpRequest
    ): TokenResponse {
        val user = request.run {
            User(
                username = username,
                password = passwordEncoder.encode(password),
                createdAt = LocalDateTime.now(),
            )
        }
        userRepository.save(user)

        return jwtTokenProvider.generateToken(user.username)
    }
}