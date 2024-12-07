package entry.dsm.gitauth.equusgithubauth.global.oauth

import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.GithubAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity

@Configuration
class GithubAuthenticationConfig{
    @Bean
    fun githubAuthenticationSuccessHandler() = GithubAuthenticationSuccessHandler()

    @Bean
    fun githubAuthenticationFailureHandler() = GithubAuthenticationFailureHandler()

    fun configureOAuth2Login(http: HttpSecurity) {
        http.oauth2Login {
            it.successHandler(githubAuthenticationSuccessHandler())
            it.failureHandler(githubAuthenticationFailureHandler())
        }
    }
}