package entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository

import org.springframework.data.jpa.repository.JpaRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User

interface UserRepository : JpaRepository<User, UUID> {
    fun findByGithubId(githubId: String): User?
}