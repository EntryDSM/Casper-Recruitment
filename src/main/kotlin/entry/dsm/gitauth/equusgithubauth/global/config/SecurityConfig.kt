package entry.dsm.gitauth.equusgithubauth.global.config

import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.CustomOAuth2AuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.CustomOAuth2AuthenticationSuccessHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.GoogleOauthService
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenFilter
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val corsConfig: CorsConfig,
    private val jwtTokenProvider: JwtTokenProvider,
    private val customOauth2UserService: GoogleOauthService,
    private val oAuth2LoginConfig: OAuth2LoginConfig,
    private val customOAuth2AuthenticationFailureHandler: CustomOAuth2AuthenticationFailureHandler,
    private val customOAuth2AuthenticationSuccessHandler: CustomOAuth2AuthenticationSuccessHandler,
) {
    companion object {
        private const val CONTENT_SECURITY_POLICY =
            "default-src 'self'; frame-ancestors 'none';"
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                .requestMatchers("/error", "/favicon.ico")
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfig.corsConfigurationSource()) }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .headers {
                it.xssProtection { xss -> xss.disable() }
                it.frameOptions { frame -> frame.deny() }
                it.contentSecurityPolicy {
                        csp ->
                    csp.policyDirectives(CONTENT_SECURITY_POLICY)
                }
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/api/google/auth/**",
                        "/api/github/auth/**",
                        "/error",
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "reports", "notices").permitAll()
                    .requestMatchers("/oauth-login/admin").hasRole("ADMIN")
                    .requestMatchers("/oauth-login/info").authenticated()
                    .anyRequest().authenticated()
            }

        oAuth2LoginConfig.configure(http)

        http
            .oauth2Login { oauth ->
                oauth.loginPage("/oauth-login/login")
                    .clientRegistrationRepository(oAuth2LoginConfig.clientRegistrationRepository())
                    .userInfoEndpoint { userInfo ->
                        userInfo.userService(customOauth2UserService)
                    }
                    .successHandler(customOAuth2AuthenticationSuccessHandler)
                    .failureHandler(customOAuth2AuthenticationFailureHandler)
                    .permitAll()
            }

        http.addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
