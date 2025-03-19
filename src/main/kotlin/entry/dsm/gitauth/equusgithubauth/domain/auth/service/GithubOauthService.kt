package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import org.springframework.stereotype.Service

@Service
class GithubOauthService(
    private val createGithubTokenService: CreateGithubTokenService,
    private val validateGithubOrganizationService: ValidateGithubOrganizationService
) {
    fun execute(
        code: String
    ) {
        val accessToken = createGithubTokenService.execute(code)
        validateGithubOrganizationService.execute(accessToken.accessToken)
    }
}