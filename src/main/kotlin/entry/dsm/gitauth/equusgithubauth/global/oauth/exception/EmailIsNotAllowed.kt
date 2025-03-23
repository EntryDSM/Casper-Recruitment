package entry.dsm.gitauth.equusgithubauth.global.oauth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class EmailIsNotAllowed : CustomException(ErrorCode.EMAIL_IS_NOT_ALLOWED) {
}