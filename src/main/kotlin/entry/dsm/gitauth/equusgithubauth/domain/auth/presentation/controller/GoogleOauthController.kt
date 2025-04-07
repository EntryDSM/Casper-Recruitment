package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.controller


import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/google/auth")
class GoogleOauthController(
    @Value("\${oauth.google.redirect}") private val googleRedirectUrl: String,
) {
    @GetMapping
    fun googleAuth(): RedirectView {
        return RedirectView(googleRedirectUrl)
    }

}
