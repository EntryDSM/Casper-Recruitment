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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
            .cors { it.configurationSource(corsConfigurationSource) }
            .csrf { it.disable() }
            .headers { it.frameOptions { frame -> frame.sameOrigin() } }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/", "/login", "/oauth2/**", "/error", "/notice/**", "/notice", "/reports").permitAll()
                    .requestMatchers(HttpMethod.GET, "reports", "notice").permitAll()
                    .anyRequest().authenticated()
            }

        githubOAuth2LoginConfig.configure(http, clientRegistrationRepository, corsConfigurationSource)

        return http.build()
    }
}
