package entry.dsm.gitauth.equusgithubauth.global.security

import entry.dsm.gitauth.equusgithubauth.global.oauth.GithubAuthenticationConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity, githubAuthenticationConfig: GithubAuthenticationConfig): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/login").permitAll()
                auth.anyRequest().authenticated()
            }

        githubAuthenticationConfig.configureOAuth2Login(http)

        return http.build()
    }
}