package entry.dsm.gitauth.equusgithubauth.domain.user.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.service.GithubUserService
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val GithubUserService: GithubUserService,
<<<<<<< HEAD
<<<<<<< HEAD
) {
    fun execute(oAuth2User: OAuth2User): User {
=======
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun  execute(oAuth2User: OAuth2User) : TokenResponse {
>>>>>>> a6e0bdb (feat : (#5) add token generation and return logic in UserService)
        val userInfo = GithubUserService.getGithubUserInformation(oAuth2User)

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
=======
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun  execute(oAuth2User: OAuth2User) : TokenResponse {
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
>>>>>>> origin/main

        val token = jwtTokenProvider.generateToken(userInfo.githubId)

        if (userRepository.findByGithubId(userInfo.githubId) != null) {
            return token
        }
        userRepository.save(user)
        return token
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/main
