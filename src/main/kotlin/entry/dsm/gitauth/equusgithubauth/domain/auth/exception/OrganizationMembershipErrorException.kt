package entry.dsm.gitauth.equusgithubauth.domain.auth.exception

import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode

class OrganizationMembershipErrorException : CustomException(ErrorCode.ORGANIZATION_MEMBERSHIP_ERROR)
