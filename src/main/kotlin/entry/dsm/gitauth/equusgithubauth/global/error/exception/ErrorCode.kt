package entry.dsm.gitauth.equusgithubauth.global.error.exception

enum class ErrorCode(
    val statusCode: Int,
    val message: String
) {
    TOKEN_EXPIRED(401, "Token Expired"),
    INVALID_TOKEN(401, "Invalid Token"),

    USER_NOT_FOUND(404, "User Not Found.")
}