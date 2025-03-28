package entry.dsm.gitauth.equusgithubauth.global.oauth.exception

import org.springframework.security.core.AuthenticationException

class EmailDomainNotAllowedException : AuthenticationException("허용되지 않은 이메일 도메인입니다")
