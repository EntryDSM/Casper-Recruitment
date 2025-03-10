package entry.dsm.gitauth.equusgithubauth.global.security.jwt

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.RefreshToken
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.security.auth.AuthDetailsService
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
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authDetailsService: AuthDetailsService,
) {
    fun generateToken(username: String): TokenResponse {
        return TokenResponse(
            accessToken = createAccessToken(username),
            accessTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
            refreshToken = createRefreshToken(username),
            refreshTokenExpiration = LocalDateTime.now().plusSeconds(jwtProperties.refreshExp),
        )
    }

    fun createAccessToken(username: String): String {
        return createToken(username, "access", jwtProperties.accessExp)
    }

    fun createRefreshToken(username: String): String {
        val refreshToken = createToken(username, "refresh", jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(
                username = username,
                refreshToken = refreshToken,
                tokenExpiration = jwtProperties.refreshExp * 1000,
            ),
        )
        return refreshToken
    }

    private fun createToken(
        username: String,
        jwtType: String,
        exp: Long,
    ): String {
        return Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()), SignatureAlgorithm.HS256)
            .setSubject(username)
            .setId(username)
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
}
