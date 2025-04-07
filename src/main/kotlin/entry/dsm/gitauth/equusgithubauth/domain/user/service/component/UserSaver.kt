package entry.dsm.gitauth.equusgithubauth.domain.user.service.component

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserSaver(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun findOrCreateUser(
        loginId: String,
        name: String,
        email: String,
    ): User {
        return userRepository.findByLoginId(loginId) ?: run {
            val newUser =
                User(
                    loginId = loginId,
                    name = name,
                    email = email,
                    role = UserRole.ADMIN,
                )
            userRepository.save(newUser)
        }
    }
}
