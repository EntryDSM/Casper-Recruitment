package entry.dsm.gitauth.equusgithubauth.global.oauth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class NotFoundOauthProvider: CustomException(ErrorCode.NOT_FOUND_OAUTH_PROVIDER) {
}