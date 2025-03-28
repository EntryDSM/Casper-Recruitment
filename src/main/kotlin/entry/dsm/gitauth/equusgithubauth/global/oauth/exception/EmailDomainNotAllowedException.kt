package entry.dsm.gitauth.equusgithubauth.global.oauth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode
import org.apache.tomcat.websocket.AuthenticationException

class EmailDomainNotAllowedException : AuthenticationException("허용되지 않은 이메일 도메인입니다")
