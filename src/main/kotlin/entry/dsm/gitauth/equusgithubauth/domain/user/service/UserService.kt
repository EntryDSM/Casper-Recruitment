package entry.dsm.gitauth.equusgithubauth.domain.user.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.service.ValidateGithubOrganizationService
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val githubApiClient: GithubApiClient,
    private val validateGithubOrganizationService: ValidateGithubOrganizationService,
) {
    fun execute(accessToken: String): LoginSuccessResponse {
        val userInfo = githubApiClient.getUser("Bearer $accessToken")
        val tokens = jwtTokenProvider.generateToken(userInfo.login)
        val isMember = validateGithubOrganizationService.execute("Bearer $accessToken")

        val user =
            User(
                loginId = userInfo.login,
                name = userInfo.name,
                email = userInfo.email,
                role = UserRole.ADMIN
            )

        /*if (userRepository.findByGithubId(userInfo.login) != null) {
            return LoginSuccessResponse(
                isMember,
                tokens.accessToken,
                tokens.accessTokenExpiration,
                tokens.refreshToken,
                tokens.refreshTokenExpiration,
            )
        }*/

        userRepository.save(user)
        return LoginSuccessResponse(
            isMember,
            tokens.accessToken,
            tokens.accessTokenExpiration,
            tokens.refreshToken,
            tokens.refreshTokenExpiration,
        )
    }
}
