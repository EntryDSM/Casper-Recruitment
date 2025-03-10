package entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User
}
