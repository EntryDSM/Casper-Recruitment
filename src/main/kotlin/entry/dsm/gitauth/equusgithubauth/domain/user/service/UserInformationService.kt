package entry.dsm.gitauth.equusgithubauth.domain.user.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.GithubUserInformation
import entry.dsm.gitauth.equusgithubauth.domain.auth.service.GithubUserService
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class UserInformationService(
    private val userRepository: UserRepository,
    private val GithubUserService: GithubUserService
) {
    fun  execute(oAuth2User: OAuth2User) : User {
        val userInfo = GithubUserService.getGithubUserInformation(oAuth2User)

        val user = User(
            githubId = userInfo.githubId,
            username = userInfo.username,
            email = userInfo.email,
            profileUrl = userInfo.profileUrl,
            avatarUrl = userInfo.avatarUrl,
            createdAt = userInfo.createdAt,
            updatedAt = userInfo.updatedAt,
            accessToken = userInfo.accessToken,
            tokenExpiration = userInfo.tokenExpiration
        )

        return userRepository.save(user)
    }
}