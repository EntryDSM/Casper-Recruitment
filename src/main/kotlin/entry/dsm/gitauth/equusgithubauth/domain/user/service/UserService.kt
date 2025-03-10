package entry.dsm.gitauth.equusgithubauth.domain.user.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.service.GithubUserService
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val githubUserService: GithubUserService,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    fun execute(oAuth2User: OAuth2User): TokenResponse {
        val userInfo = githubUserService.getGithubUserInformation(oAuth2User)

        val user =
            User(
                githubId = userInfo.githubId,
                username = userInfo.username,
                email = userInfo.email,
                profileUrl = userInfo.profileUrl,
                avatarUrl = userInfo.avatarUrl,
                createdAt = userInfo.createdAt,
                updatedAt = userInfo.updatedAt,
                accessToken = userInfo.accessToken,
                tokenExpiration = userInfo.tokenExpiration,
            )

        val token = jwtTokenProvider.generateToken(userInfo.githubId)

        if (userRepository.findByGithubId(userInfo.githubId) != null) {
            return token
        }
        userRepository.save(user)
        return token
    }
}
