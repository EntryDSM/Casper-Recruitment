package entry.dsm.gitauth.equusgithubauth.domain.user.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.OrganizationMembershipErrorException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.CreateGithubTokenService
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.ValidateGithubOrganizationService
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import feign.FeignException
import feign.Response
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val githubApiClient: GithubApiClient,
    private val validateGithubOrganizationService: ValidateGithubOrganizationService
) {
    fun execute(accessToken: String): LoginSuccessResponse {
        val userInfo = githubApiClient.getUser("Bearer $accessToken")
        val tokens = jwtTokenProvider.generateToken(userInfo.login)
        val isUser = validateGithubOrganizationService.execute("Bearer $accessToken", userInfo.login)

        val user =
            User(
                githubId = userInfo.login,
                username = userInfo.name,
                email = userInfo.email,
                profileUrl = userInfo.htmlUrl,
                avatarUrl = userInfo.avatarUrl,
                createdAt = userInfo.createdAt,
                updatedAt = userInfo.updatedAt,
                accessToken = tokens.accessToken,
                tokenExpiration = tokens.accessTokenExpiration
            )

        if (userRepository.findByGithubId(userInfo.login) != null) {
            return LoginSuccessResponse(
                isUser,
                tokens.accessToken,
                tokens.accessTokenExpiration,
                tokens.refreshToken,
                tokens.refreshTokenExpiration
            )
        }

        userRepository.save(user)
        return LoginSuccessResponse(
            isUser,
            tokens.accessToken,
            tokens.accessTokenExpiration,
            tokens.refreshToken,
            tokens.refreshTokenExpiration
        )
    }
}
