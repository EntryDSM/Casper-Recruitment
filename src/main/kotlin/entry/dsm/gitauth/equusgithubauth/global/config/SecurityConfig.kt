package entry.dsm.gitauth.equusgithubauth.global.config


import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.CustomOAuth2AuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.CustomOAuth2AuthenticationSuccessHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.GoogleOauthService
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenFilter
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import jakarta.servlet.http.HttpServletResponse
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
    private val customOAuth2AuthenticationSuccessHandler: CustomOAuth2AuthenticationSuccessHandler
) {

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
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/", "/login", "/oauth2/**", "/api/github/auth", "/api/github/auth/**",
                        "/oauth2/authorize/**", "/error"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "reports", "notice").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/oauth-login/admin").hasRole("ADMIN")
                    .requestMatchers("/oauth-login/info").authenticated()
                    .anyRequest().authenticated()
            }
            .exceptionHandling { exception ->
                exception.authenticationEntryPoint { request, response, authException ->
                    response.status = HttpServletResponse.SC_BAD_REQUEST
                    response.contentType = "application/json; charset=UTF-8"
                    val errorMessage = authException?.message ?: "허용되지 않은 요청입니다."
                    response.writer.write("""{ "error": "요청 실패", "message": "$errorMessage" }""")
                }
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
                    .failureHandler(customOAuth2AuthenticationFailureHandler) // 주입된 빈 사용
                    .permitAll()
            }


        http.addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}