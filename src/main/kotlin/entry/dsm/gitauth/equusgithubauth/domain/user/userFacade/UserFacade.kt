package entry.dsm.gitauth.equusgithubauth.domain.user.userFacade

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.global.security.auth.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
) {
    fun findByUserName(userName: String): User {
        return userRepository.findByUserName(userName)
            ?: throw UserNotFoundException
    }
}
