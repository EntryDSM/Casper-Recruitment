package entry.dsm.gitauth.equusgithubauth.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val excludedPaths: List<String> = listOf(
        "/api/github/auth",
        "/oauth2/authorization/github",
        "/login/oauth2/code/github",
        "/api/google/auth"
    )
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val requestURI = request.requestURI
        if (excludedPaths.any { requestURI.startsWith(it) }) {
            filterChain.doFilter(request, response)
            return
        }

        val bearer = jwtTokenProvider.resolveToken(request)

        if (bearer != null) {
            val authentication: Authentication = jwtTokenProvider.getAuthentication(bearer)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
