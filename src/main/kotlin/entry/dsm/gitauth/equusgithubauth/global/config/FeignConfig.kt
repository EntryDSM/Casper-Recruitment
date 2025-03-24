package entry.dsm.gitauth.equusgithubauth.global.config

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubProviderProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubRegistrationProperties
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller"])
@EnableConfigurationProperties(
    value = [
        JwtProperties::class,
        GithubRegistrationProperties::class,
        GithubProviderProperties::class,
    ],
)
@Configuration
class FeignConfig
