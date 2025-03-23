package entry.dsm.gitauth.equusgithubauth.global.oauth.service.component

import entry.dsm.gitauth.equusgithubauth.global.oauth.exception.EmailIsNotAllowed
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EmailDomainValidator(
    @Value("\${email.allowed}") private val allowedDomain: String,
) {
    fun isAllowed(email: String): Boolean {
        if (!email.endsWith("@$allowedDomain")) {
            throw EmailIsNotAllowed()
        }
        return true
    }
}
