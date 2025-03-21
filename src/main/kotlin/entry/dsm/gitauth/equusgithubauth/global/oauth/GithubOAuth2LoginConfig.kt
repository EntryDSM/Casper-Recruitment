package entry.dsm.gitauth.equusgithubauth.global.oauth

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubRegistrationProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.GithubProviderProperties
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Configuration
class GithubOAuth2LoginConfig(
    private val githubRegistrationProperties: GithubRegistrationProperties,
    private val githubProviderProperties: GithubProviderProperties
) {

    @Bean
    fun githubAuthenticationSuccessHandler() = GithubAuthenticationSuccessHandler()

    @Bean
    fun githubAuthenticationFailureHandler() = GithubAuthenticationFailureHandler()

    fun configure(
        http: HttpSecurity,
    ) {
        http.oauth2Login { oauth ->
            oauth
                .successHandler(githubAuthenticationSuccessHandler())
                .failureHandler(githubAuthenticationFailureHandler())
                .authorizationEndpoint { authorizationEndpoint ->
                    val defaultResolver = DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository(),
                        "/oauth2/authorization"
                    )
                    defaultResolver.setAuthorizationRequestCustomizer { builder ->
                        builder.scope("read:org")
                    }
                    authorizationEndpoint.authorizationRequestResolver(defaultResolver)
                }
        }
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val githubRegistration = ClientRegistration
            .withRegistrationId("github")
            .clientId(githubRegistrationProperties.clientId)
            .clientSecret(githubRegistrationProperties.clientSecret)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(githubRegistrationProperties.redirectUri)
            .scope(githubRegistrationProperties.scope)
            .authorizationUri(githubProviderProperties.authorizationUri)
            .tokenUri(githubProviderProperties.tokenUri)
            .userInfoUri(githubProviderProperties.userInfoUri)
            .userNameAttributeName(githubProviderProperties.userNameAttribute)
            .clientName(githubRegistrationProperties.clientName)
            .build()

        return InMemoryClientRegistrationRepository(githubRegistration)
    }

}