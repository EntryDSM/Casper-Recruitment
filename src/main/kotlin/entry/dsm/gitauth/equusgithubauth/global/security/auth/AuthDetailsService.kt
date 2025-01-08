package entry.dsm.gitauth.equusgithubauth.global.security.auth

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(githubId: String): UserDetails {
        val user: User =
            userRepository.findByGithubId(githubId)
                ?: throw IllegalArgumentException("User not found with githubId: $githubId")

        return AuthDetails(user)
    }
}
