package entry.dsm.gitauth.equusgithubauth.domain.auth.entity.repository

import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByRefreshToken(token: String): RefreshToken?
}
