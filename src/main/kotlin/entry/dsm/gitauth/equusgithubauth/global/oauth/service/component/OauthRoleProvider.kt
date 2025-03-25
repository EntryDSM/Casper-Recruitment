package entry.dsm.gitauth.equusgithubauth.global.oauth.service.component

import entry.dsm.gitauth.equusgithubauth.domain.auth.enums.OauthType
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import entry.dsm.gitauth.equusgithubauth.global.oauth.exception.NotFoundOauthProvider
import org.springframework.stereotype.Component

@Component
class OauthRoleProvider {

    fun getRoleByProvider(provider: String): UserRole {
        return when (provider) {
            OauthType.GITHUB.provider -> UserRole.ADMIN
            OauthType.GOOGLE.provider -> UserRole.USER
            else -> throw NotFoundOauthProvider()
        }
    }
}