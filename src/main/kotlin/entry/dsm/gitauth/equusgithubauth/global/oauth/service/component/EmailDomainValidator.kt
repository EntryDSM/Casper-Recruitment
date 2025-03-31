package entry.dsm.gitauth.equusgithubauth.global.oauth.service.component

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomOauth2AuthenticationException
import entry.dsm.gitauth.equusgithubauth.global.oauth.exception.EmailDomainNotAllowedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EmailDomainValidator(
    @Value("\${email.allowed}") private val allowedDomain: String,
) {
    fun isAllowed(email: String): Boolean {
        if (!email.endsWith("@$allowedDomain")) {
            // EmailDomainNotAllowedException 대신 CustomOauth2AuthenticationException을 던짐
            throw CustomOauth2AuthenticationException("허용되지 않은 이메일 도메인입니다.", EmailDomainNotAllowedException())
        }
        return true
    }
}
