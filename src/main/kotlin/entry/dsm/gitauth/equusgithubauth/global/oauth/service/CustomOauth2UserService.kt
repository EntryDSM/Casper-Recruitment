package entry.dsm.gitauth.equusgithubauth.global.oauth.service

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.oauth.OAuth2UserInfo
import entry.dsm.gitauth.equusgithubauth.global.security.auth.CustomOauth2UserDetails
import entry.dsm.gitauth.equusgithubauth.global.security.auth.GoogleUserDetails
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val googleOauthUserService: GoogleOauthUserService
) : DefaultOAuth2UserService() {

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val ACCESS_TOKEN_EXPIRATION = "accessTokenExpiration"
        private const val REFRESH_TOKEN = "refreshToken"
        private const val REFRESH_TOKEN_EXPIRATION = "refreshTokenExpiration"
    }

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val provider = userRequest.clientRegistration.registrationId

        val oAuth2UserInfo: OAuth2UserInfo = when(provider) {
            "google" -> GoogleUserDetails(oAuth2User.attributes)
            else -> throw UnsupportedOperationException(provider)
        }

        val loginId = "$provider${oAuth2UserInfo.getProviderId()}"

        val user = googleOauthUserService.findOrCreateOAuthUser(
            loginId = loginId,
            email = oAuth2UserInfo.getEmail(),
            name = oAuth2UserInfo.getName(),
            provider = provider,
            providerId = oAuth2UserInfo.getProviderId()
        )

        val tokenResponse: TokenResponse = jwtTokenProvider.generateToken(loginId)

        // OAuth2User의 attributes에 토큰 정보를 추가하여 클라이언트 전달에 사용
        val updatedAttributes = oAuth2User.attributes.toMutableMap().apply {
            put(ACCESS_TOKEN, tokenResponse.accessToken)
            put(ACCESS_TOKEN_EXPIRATION, tokenResponse.accessTokenExpiration.toString())
            put(REFRESH_TOKEN, tokenResponse.refreshToken)
            put(REFRESH_TOKEN_EXPIRATION, tokenResponse.refreshTokenExpiration.toString())
        }

        return CustomOauth2UserDetails(user, updatedAttributes)
    }
}
