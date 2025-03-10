package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.LoginRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.SignUpRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.SignUpService
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.LoginService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val signUpService: SignUpService,
    private val loginService: LoginService
) {
    @PostMapping("/sign-up")
    fun signUp(
        request: SignUpRequest
    ): TokenResponse {
        return signUpService.execute(request)
    }

    @PostMapping("login")
    fun login(
        request: LoginRequest
    ): TokenResponse {
        return loginService.execute(request)
    }
}