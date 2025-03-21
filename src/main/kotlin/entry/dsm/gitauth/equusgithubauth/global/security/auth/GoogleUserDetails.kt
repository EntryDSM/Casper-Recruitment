package entry.dsm.gitauth.equusgithubauth.global.security.auth

import entry.dsm.gitauth.equusgithubauth.global.oauth.OAuth2UserInfo

data class GoogleUserDetails(
    private val attributes: Map<String, Any>
) : OAuth2UserInfo {

    override fun getProvider(): String {
        return "google"
    }

    override fun getProviderId(): String {
        return attributes["sub"] as String
    }

    override fun getEmail(): String {
        return attributes["email"] as String
    }

    override fun getName(): String {
        return attributes["name"] as String
    }
}
