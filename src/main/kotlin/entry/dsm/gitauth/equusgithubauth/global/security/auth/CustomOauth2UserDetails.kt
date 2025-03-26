package entry.dsm.gitauth.equusgithubauth.global.security.auth

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOauth2UserDetails(
    private val user: User,
    private val attributes: Map<String, Any>,
) : UserDetails, OAuth2User {
    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    override fun getName(): String {
        return "" // OAuth2에서 getName은 필요에 맞게 처리할 수 있다
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(user.role.name))
        return authorities
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return user.loginId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
