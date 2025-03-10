package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.SignUpRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.SignUpService
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val signUpService: SignUpService
) {
    @PostMapping("/sign-up")
    fun signUp(
        request: SignUpRequest
    ): TokenResponse {
        return signUpService.execute(request)
    }
}