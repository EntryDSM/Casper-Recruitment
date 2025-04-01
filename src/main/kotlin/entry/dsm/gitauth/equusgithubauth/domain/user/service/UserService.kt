package entry.dsm.gitauth.equusgithubauth.domain.user.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.ValidateGithubOrganizationService
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.UnAuthorizedOrgAccessException
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.component.OauthRoleProvider
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val githubApiClient: GithubApiClient,
    private val validateGithubOrganizationService: ValidateGithubOrganizationService,
    private val oauthRoleProvider: OauthRoleProvider,
) {
    companion object {
        private fun withBearer(accessToken: String) = "Bearer $accessToken"
        private const val GITHUB = "GITHUB"
    }

    fun execute(accessToken: String): LoginSuccessResponse {
        val bearerToken = withBearer(accessToken)
        val userInfo = githubApiClient.getUser(bearerToken)
        val tokens = jwtTokenProvider.generateToken(userInfo.login)

        if (!validateGithubOrganizationService.execute(bearerToken)) {
            throw UnAuthorizedOrgAccessException()
        }

        val provider = GITHUB // 지금 GitHub OAuth 로직에는 provider가 없으므로 하드코딩

        val user =
            User(
                loginId = userInfo.login,
                name = userInfo.name,
                email = userInfo.email,
                role = oauthRoleProvider.getRoleByProvider(provider),
            )

        userRepository.save(user)
        return LoginSuccessResponse(
            tokens.accessToken,
            tokens.accessTokenExpiration,
            tokens.refreshToken,
            tokens.refreshTokenExpiration,
        )
    }
}
