package entry.dsm.gitauth.equusgithubauth.global.config

import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationSuccessHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubAuthProperties
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubProviderProperties
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubRegistrationProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Configuration
class OAuth2LoginConfig(
    private val githubRegistrationProperties: GithubRegistrationProperties,
    private val githubProviderProperties: GithubProviderProperties,
    private val githubAuthProperties: GithubAuthProperties,
    @Value("\${google.client-id}") private val googleClientId: String,
    @Value("\${google.secret}") private val googleSecret: String,
    @Value("\${google.uri}") private val googleUri: String,
) {
    companion object {
        private const val GITHUB_REGISTRATION_ID = "github"
        private const val GOOGLE_REGISTRATION_ID = "google"
    }

    @Bean
    fun githubAuthenticationSuccessHandler() = GithubAuthenticationSuccessHandler(githubAuthProperties)

    @Bean
    fun githubAuthenticationFailureHandler() = GithubAuthenticationFailureHandler(githubAuthProperties)

    fun configure(http: HttpSecurity) {
        http.oauth2Login { oauth ->
            oauth
                .successHandler(githubAuthenticationSuccessHandler())
                .failureHandler(githubAuthenticationFailureHandler())
                .authorizationEndpoint { authorizationEndpoint ->
                    val defaultResolver =
                        DefaultOAuth2AuthorizationRequestResolver(
                            clientRegistrationRepository(),
                            "/oauth2/authorization",
                        )

                    defaultResolver.setAuthorizationRequestCustomizer { builder ->
                        val requestUri = builder.build().authorizationRequestUri
                        val registrationId = requestUri.substringAfterLast("/") // "google" or "github" 추출

                        if (registrationId == GITHUB_REGISTRATION_ID) {
                            builder.scope("read:org") // GitHub일 때만 read:org 추가
                        }
                    }

                    authorizationEndpoint.authorizationRequestResolver(defaultResolver)
                }
        }
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val githubRegistration =
            ClientRegistration
                .withRegistrationId(GITHUB_REGISTRATION_ID)
                .clientId(githubRegistrationProperties.clientId)
                .clientSecret(githubRegistrationProperties.clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(githubRegistrationProperties.redirectUrl)
                .scope(githubRegistrationProperties.scope)
                .authorizationUri(githubProviderProperties.authorizationUrl)
                .tokenUri(githubProviderProperties.tokenUrl)
                .userInfoUri(githubProviderProperties.userInfoUrl)
                .userNameAttributeName(githubProviderProperties.userNameAttribute)
                .clientName(githubRegistrationProperties.clientName)
                .build()

        val googleRegistration =
            CommonOAuth2Provider.GOOGLE.getBuilder(GOOGLE_REGISTRATION_ID)
                .clientId(googleClientId)
                .clientSecret(googleSecret)
                .redirectUri(googleUri)
                .scope(
                    "https://www.googleapis.com/auth/userinfo.email",
                    "https://www.googleapis.com/auth/userinfo.profile",
                )
                .build()

        return InMemoryClientRegistrationRepository(githubRegistration, googleRegistration)
    }
}
