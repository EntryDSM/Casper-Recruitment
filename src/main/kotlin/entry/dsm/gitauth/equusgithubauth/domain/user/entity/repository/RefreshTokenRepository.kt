package entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String>