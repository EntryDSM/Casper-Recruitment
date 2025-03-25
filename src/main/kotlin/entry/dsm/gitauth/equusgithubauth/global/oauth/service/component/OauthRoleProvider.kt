package entry.dsm.gitauth.equusgithubauth.global.oauth.service.component

import entry.dsm.gitauth.equusgithubauth.domain.auth.enums.OauthType
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import entry.dsm.gitauth.equusgithubauth.global.oauth.exception.NotFoundOauthProvider
import org.springframework.stereotype.Component

@Component
class OauthRoleProvider {

    fun getRoleByProvider(provider: String): UserRole {
        return if (provider == OauthType.GITHUB.provider) UserRole.ADMIN else UserRole.USER
    }
}
