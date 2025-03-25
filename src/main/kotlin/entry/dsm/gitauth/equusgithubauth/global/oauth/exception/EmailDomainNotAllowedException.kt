package entry.dsm.gitauth.equusgithubauth.global.oauth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class EmailDomainNotAllowedException : CustomException(ErrorCode.EMAIL_DOMAIN_IN_NOT_ALLOWED_EXCEPTION) {
}