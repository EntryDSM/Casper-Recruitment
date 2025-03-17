package entry.dsm.gitauth.equusgithubauth.global.oauth.service

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
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
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val provider = userRequest.clientRegistration.registrationId


        val oAuth2UserInfo: OAuth2UserInfo = when(provider) {
            "google" -> GoogleUserDetails(oAuth2User.attributes)
            else -> throw OAuth2AuthenticationException("지원하지 않는 provider: $provider")
        }

        val providerId = oAuth2UserInfo.getProviderId()
        val loginId = "$provider$providerId"
        val email = oAuth2UserInfo.getEmail()
        val name = oAuth2UserInfo.getName()


        val user = userRepository.findByLoginId(loginId) ?: run {
            User(
                loginId = loginId,
                email = email,
                password = "", // OAuth는 비밀번호가 필요 없음
                name = name,
                provider = provider,
                providerId = providerId,
                role = UserRole.USER
            ).also { userRepository.save(it) }
        }


        val tokenResponse: TokenResponse = jwtTokenProvider.generateToken(loginId)

        // OAuth2User의 attributes에 토큰 정보를 추가하여 클라이언트 전달에 사용
        val updatedAttributes = oAuth2User.attributes.toMutableMap().apply {
            put("accessToken", tokenResponse.accessToken)
            put("accessTokenExpiration", tokenResponse.accessTokenExpiration.toString())
            put("refreshToken", tokenResponse.refreshToken)
            put("refreshTokenExpiration", tokenResponse.refreshTokenExpiration.toString())
        }

        return CustomOauth2UserDetails(user, updatedAttributes)
    }
}
