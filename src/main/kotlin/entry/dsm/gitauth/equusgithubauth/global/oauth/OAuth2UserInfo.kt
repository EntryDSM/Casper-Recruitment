package entry.dsm.gitauth.equusgithubauth.global.oauth

interface OAuth2UserInfo {
    fun getProvider(): String
    fun getProviderId(): String
    fun getEmail(): String
    fun getName(): String
}
