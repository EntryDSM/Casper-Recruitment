package entry.dsm.gitauth.equusgithubauth.global.security.auth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class UserNotFoundException : CustomException(ErrorCode.USER_NOT_FOUND)
