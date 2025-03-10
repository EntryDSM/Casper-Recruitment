package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.LoginRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.SignUpRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.TokenRefreshRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.SignUpService
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.LoginService
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.TokenRefreshService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val signUpService: SignUpService,
    private val loginService: LoginService,
    private val tokenRefreshService: TokenRefreshService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(
        @Valid
        @RequestBody request: SignUpRequest
    ): TokenResponse {
        return signUpService.execute(request)
    }

    @PostMapping("login")
    fun login(
        @Valid
        @RequestBody request: LoginRequest
    ): TokenResponse {
        return loginService.execute(request)
    }

    @PostMapping("token-refresh")
    fun tokenRefresh(
        @RequestBody request: TokenRefreshRequest
    ): TokenResponse {
        return tokenRefreshService.execute(request)
    }
}