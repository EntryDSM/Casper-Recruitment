package entry.dsm.gitauth.equusgithubauth.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.CustomOAuth2AuthenticationFailureHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.handler.CustomOAuth2AuthenticationSuccessHandler
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.CustomOauth2UserService
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
    private val customOauth2UserService: CustomOauth2UserService,
    private val objectMapper: ObjectMapper
    private val githubOAuth2LoginConfig: GithubOAuth2LoginConfig,
    private val corsConfig: CorsConfig,
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        // security를 적용하지 않을 리소스
        return WebSecurityCustomizer { web ->
            web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico")
        }
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
    ): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfig.corsConfigurationSource()) }
            .formLogin { it.disable() }

                // 세션 관리 설정 (무상태 세션)
                http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }


                http
                    .authorizeHttpRequests { auth ->
                        auth
                            .requestMatchers("/", "/login", "/oauth2/**", "/error", "/notice/**", "/notice", "/reports")
                            .permitAll()
                            .requestMatchers(HttpMethod.GET, "reports", "notice").permitAll()
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers("/oauth-login/admin").hasRole("ADMIN") // 특정 URL에 대한 권한 설정
                            .requestMatchers("/oauth-login/info").authenticated() // 인증된 사용자만 접근 허용
                            .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 접근 가능
                    }

                // OAuth 2.0 로그인 설정
                    .oauth2Login { oauth ->
                        oauth.loginPage("/oauth-login/login")
                            .userInfoEndpoint { userInfo ->
                                // 여기서 CustomOauth2AuthService를 등록합니다.
                                userInfo.userService(customOauth2UserService)
                            }
                            .successHandler(CustomOAuth2AuthenticationSuccessHandler(objectMapper))
                            .failureHandler(CustomOAuth2AuthenticationFailureHandler())
                            .permitAll()
                    }
                    .addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)



                return http.build()
            }


    }
