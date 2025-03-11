package entry.dsm.gitauth.equusgithubauth.global.security.jwt

import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.RefreshToken
import entry.dsm.gitauth.equusgithubauth.domain.auth.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.auth.AuthDetailsService
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception.JwtTokenExpiredException
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.exception.JwtTokenInvalidException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
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
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authDetailsService: AuthDetailsService,
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    fun generateToken(userName: String): TokenResponse {
        return TokenResponse(
            accessToken = createAccessToken(userName),
            accessTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
            refreshToken = createRefreshToken(userName),
            refreshTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp),
        )
    }

    fun createAccessToken(userName: String): String {
        return createToken(userName, ACCESS_KEY, jwtProperties.accessExp)
    }

    fun createRefreshToken(userName: String): String {
        val refreshToken = createToken(userName, REFRESH_KEY, jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(
                userName = userName,
                refreshToken = refreshToken,
                tokenExpiration = jwtProperties.refreshExp * 1000,
            ),
        )
        return refreshToken
    }

    private fun createToken(
        userName: String,
        type: String,
        exp: Long,
    ): String {
        return Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()), SignatureAlgorithm.HS256)
            .setSubject(userName)
            .setId(userName)
            .claim("type", type)
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

    fun reissue(
        refreshToken: String
    ): TokenResponse {
        if (isNotRefreshToken(refreshToken)) {
            throw JwtTokenInvalidException
        }

        refreshTokenRepository.findByRefreshToken(refreshToken)
            ?.let { token ->
                val userName = token.userName
                val tokenResponse = generateToken(userName)

                token.updateToken(refreshToken, jwtProperties.refreshExp)
                return TokenResponse(
                    tokenResponse.accessToken,
                    tokenResponse.accessTokenExpiration,
                    tokenResponse.refreshToken,
                    tokenResponse.refreshTokenExpiration
                )
            }?: throw JwtTokenInvalidException
    }

    private fun isNotRefreshToken(
        token: String?
    ): Boolean {
        return REFRESH_KEY != getJws(token!!).header["typ"].toString()
    }

    private fun getJws(
        token: String
    ): Jws<Claims> {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw JwtTokenExpiredException
        } catch (e: Exception) {
            throw JwtTokenInvalidException
        }
    }
}
