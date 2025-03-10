package entry.dsm.gitauth.equusgithubauth.global.util

object RegexpUtil {
    const val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{10,30}\$\n"
}