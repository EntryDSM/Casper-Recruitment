package entry.dsm.gitauth.equusgithubauth.global.security.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('USER')")
annotation class OnlyUser
