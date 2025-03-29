package entry.dsm.gitauth.equusgithubauth.global.oauth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.enums.OauthType
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.UnsupportedProviderException
import entry.dsm.gitauth.equusgithubauth.global.oauth.OAuth2UserInfo
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.component.EmailDomainValidator
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.component.GoogleOauthUserService
import entry.dsm.gitauth.equusgithubauth.global.security.auth.CustomOauth2UserDetails
import entry.dsm.gitauth.equusgithubauth.global.security.auth.GoogleUserDetails
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class GoogleOauthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val googleOauthUserService: GoogleOauthUserService,
    private val emailDomainValidator: EmailDomainValidator,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val provider = userRequest.clientRegistration.registrationId

        val oAuth2UserInfo: OAuth2UserInfo =
            when (provider) {
                OauthType.GOOGLE.provider -> GoogleUserDetails(oAuth2User.attributes)
                else -> throw UnsupportedProviderException(provider)
            }

        val email = oAuth2UserInfo.getEmail()

        // 이메일 검증 학교 도메인 외 로그인 못함
        emailDomainValidator.isAllowed(email)

        val loginId = "$provider${oAuth2UserInfo.getProviderId()}"

        val user =
            googleOauthUserService.findOrCreateOAuthUser(
                loginId = loginId,
                email = oAuth2UserInfo.getEmail(),
                name = oAuth2UserInfo.getName(),
                provider = provider,
                providerId = oAuth2UserInfo.getProviderId(),
            )

        return CustomOauth2UserDetails(user, oAuth2User.attributes)
    }
}
