package entry.dsm.gitauth.equusgithubauth.global.external.service

import org.springframework.stereotype.Component

@Component
class TokenAuthenticator {
    fun createAuthorizationHeader(token: String): String {
        return "Bearer $token"
    }
}
