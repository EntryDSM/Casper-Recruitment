package entry.dsm.gitauth.equusgithubauth.global.oauth.service.component

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GoogleOauthUserService(
    private val userRepository: UserRepository,
    private val oauthRoleProvider: OauthRoleProvider,
) {
    @Transactional
    fun findOrCreateOAuthUser(
        loginId: String,
        email: String,
        name: String,
        provider: String,
        providerId: String,
    ): User {
        return userRepository.findByLoginId(loginId) ?: run {
            User(
                loginId = loginId,
                email = email,
                name = name,
                provider = provider,
                providerId = providerId,
                role = oauthRoleProvider.getRoleByProvider(provider),
            ).also { userRepository.save(it) }
        }
    }
}
