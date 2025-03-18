package entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UserRepository : CrudRepository<User, UUID> {
    fun findByLoginId(loginId: String): User?
}
