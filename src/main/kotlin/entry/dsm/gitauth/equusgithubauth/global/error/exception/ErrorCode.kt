package entry.dsm.gitauth.equusgithubauth.global.error.exception

enum class ErrorCode(
    val statusCode: Int,
    val message: String
) {
    USER_NOT_FOUND(404, "User Not Found.")
}