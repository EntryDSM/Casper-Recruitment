package entry.dsm.gitauth.equusgithubauth.global.security.auth

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

class AuthDetails(
    val user: User
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singleton(SimpleGrantedAuthority(user.githubId))
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return user.githubId
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