package entry.dsm.gitauth.equusgithubauth.domain.auth.exception

import org.springframework.security.oauth2.core.OAuth2AuthenticationException

class UnsupportedProviderException(provider : String) : OAuth2AuthenticationException(provider) {
}