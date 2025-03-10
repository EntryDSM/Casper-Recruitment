package entry.dsm.gitauth.equusgithubauth.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val corsConfig: CorsConfig
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        clientRegistrationRepository: ClientRegistrationRepository,
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfig.corsConfigurationSource()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/", "/login", "/oauth2/**", "/error", "/notice/**", "/notice", "/reports").permitAll()
                    .requestMatchers(HttpMethod.GET, "reports", "notice").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
