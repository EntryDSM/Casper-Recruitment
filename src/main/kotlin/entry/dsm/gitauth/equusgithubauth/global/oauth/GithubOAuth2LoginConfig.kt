package entry.dsm.gitauth.equusgithubauth.global.oauth

import entry.dsm.gitauth.equusgithubauth.global.config.CorsConfig
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class GithubOAuth2LoginConfig(
    private val corsConfig: CorsConfig
) {
    @Bean
    fun githubAuthenticationSuccessHandler() = GithubAuthenticationSuccessHandler()

    @Bean
    fun githubAuthenticationFailureHandler() = GithubAuthenticationFailureHandler()

    fun configure(
        http: HttpSecurity,
        clientRegistrationRepository: ClientRegistrationRepository,
    ) {
        http
            .cors { it.configurationSource(corsConfig.corsConfigurationSource()) }
            .oauth2Login { oauth ->
                oauth
                    .successHandler(githubAuthenticationSuccessHandler())
                    .failureHandler(githubAuthenticationFailureHandler())
                    .authorizationEndpoint { authorizationEndpoint ->
                       val defaultResolver =
                            DefaultOAuth2AuthorizationRequestResolver(
                            clientRegistrationRepository,
                                "/oauth2/authorization",
                            )
                        defaultResolver.setAuthorizationRequestCustomizer { builder ->
                            builder.scope("read:org")
                        }
                        authorizationEndpoint.authorizationRequestResolver(defaultResolver)
                    }
        }
    }
}
