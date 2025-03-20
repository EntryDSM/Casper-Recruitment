package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.OrganizationMembershipErrorException
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class ValidateGithubOrganizationService(
    private val githubApiClient: GithubApiClient
) {
    fun execute(accessToken: String, username: String): Boolean {
        return try {
            githubApiClient.checkOrganization(accessToken, username)
            false
        } catch (e: FeignException.NotFound) {
            true
        } catch (e: FeignException) {
            throw OrganizationMembershipErrorException
        }
    }
}