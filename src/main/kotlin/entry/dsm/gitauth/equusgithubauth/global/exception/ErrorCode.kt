package entry.dsm.gitauth.equusgithubauth.global.exception

enum class ErrorCode(
    val status: Int,
    val message: String,
) {
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    NOTICE_NOT_FOUND(404, "공지사항을 찾을 수 없습니다."),

    REPORT_NOT_FOUND(404, "보고서를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "refreshToken not found."),
    JWT_TOKEN_EXPIRED(401, "JWT 토큰이 만료되었습니다."),
    JWT_TOKEN_INVALID(401, "JWT 토큰이 유효하지 않습니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),
}
