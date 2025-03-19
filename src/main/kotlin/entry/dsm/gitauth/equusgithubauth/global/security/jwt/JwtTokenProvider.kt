package entry.dsm.gitauth.equusgithubauth.global.security.jwt


import entry.dsm.gitauth.equusgithubauth.domain.user.entity.RefreshToken
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.UserRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.auth.AuthDetailsService
import entry.dsm.gitauth.equusgithubauth.global.security.auth.exception.RefreshTokenNotFoundException
import entry.dsm.gitauth.equusgithubauth.global.security.auth.exception.UserNotFoundException
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception.JwtTokenExpiredException
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception.JwtTokenInvalidException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.Date

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun generateToken(loginId: String): TokenResponse {
        return TokenResponse(
            accessToken = createAccessToken(loginId),
            accessTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
            refreshToken = createRefreshToken(loginId),
            refreshTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp),
        )
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }

    fun createAccessToken(loginId: String): String {
        return createToken(loginId, ACCESS_TOKEN, jwtProperties.accessExp)
    }

    fun createRefreshToken(loginId: String): String {
        val refreshToken = createToken(loginId, REFRESH_TOKEN, jwtProperties.refreshExp)

        val refreshTokenEntity = RefreshToken(
            loginId = loginId,
            refreshToken = refreshToken,
            tokenExpiration = jwtProperties.refreshExp
        )

        refreshTokenRepository.save(refreshTokenEntity)

        return refreshToken
    }


    private fun createToken(
        loginId: String,
        jwtType: String,
        exp: Long,
    ): String {
        return Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()), SignatureAlgorithm.HS256)
            .setSubject(loginId)
            .setId(loginId)
            .claim("type", jwtType)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(jwtProperties.header)

        if (bearerToken != null && (bearerToken.startsWith(jwtProperties.header))) {
            return bearerToken.substring(7)
        }
        return null
    }

    fun reissueToken(storedToken: RefreshToken): TokenResponse {
        val loginId = storedToken.loginId

        val newAccessToken = createAccessToken(loginId)
        val newRefreshToken = createRefreshToken(loginId)

        storedToken.refreshToken = newRefreshToken

        refreshTokenRepository.save(storedToken)

        return TokenResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            accessTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
            refreshTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp)
        )
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = authDetailsService.loadUserByUsername(getClaims(token).subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getClaims(token: String): Claims {
        return try {
            Jwts
                .parser()
                .setSigningKey(jwtProperties.secretKey)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw JwtTokenExpiredException
                else -> throw JwtTokenInvalidException
            }
        }
    }
    fun valid(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: JwtTokenExpiredException) {
            throw e
        } catch (e: JwtTokenInvalidException) {
            throw e
        }
    }

    fun getExpiration(token: String): Long {
        return getClaims(token).expiration.time - System.currentTimeMillis()
    }

    fun getSubjectFromToken(token: String): String {
        return getClaims(token).subject
    }

}
