package entry.dsm.gitauth.equusgithubauth.global.oauth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class AuthenticationPrincipalMismatch : CustomException(ErrorCode.AUTHENTICATION_PRINCIPAL_MISMATCH)
