package entry.dsm.gitauth.equusgithubauth.global.oauth.service

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class GoogleOauthUserService(
    private val userRepository: UserRepository
) {
    fun findOrCreateOAuthUser(
        loginId: String,
        email: String,
        name: String,
        provider: String,
        providerId: String
    ): User {
        return userRepository.findByLoginId(loginId) ?: run {
            User(
                loginId = loginId,
                email = email,
                password = "",
                name = name,
                provider = provider,
                providerId = providerId,
                role = UserRole.USER
            ).also { userRepository.save(it) }
        }
    }
}