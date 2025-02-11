package entry.dsm.gitauth.equusgithubauth.global.config

import entry.dsm.gitauth.equusgithubauth.global.oauth.GithubOAuth2LoginConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val githubOAuth2LoginConfig: GithubOAuth2LoginConfig,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        clientRegistrationRepository: ClientRegistrationRepository,
        corsConfigurationSource: CorsConfigurationSource
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource) }
            .headers { it.frameOptions { frame -> frame.sameOrigin() } }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/", "/login", "/oauth2/**", "/error").permitAll()
                    .requestMatchers(HttpMethod.GET, "reports", "notice").permitAll()
                    .anyRequest().authenticated()
            }

        githubOAuth2LoginConfig.configure(http, clientRegistrationRepository)

        return http.build()
    }
}
