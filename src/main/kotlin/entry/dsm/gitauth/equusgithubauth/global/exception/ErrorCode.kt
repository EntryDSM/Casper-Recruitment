package entry.dsm.gitauth.equusgithubauth.global.exception

enum class ErrorCode(
    val status: Int,
    val message: String,
) {
    USER_INFO_FETCH_ERROR(500, "GitHub 사용자 정보를 가져올 수 없습니다."),
    AUTHORIZED_CLIENT_NOT_FOUND(400, "사용자에 대한 인증된 클라이언트를 찾을 수 없습니다."),
    ORGANIZATION_MEMBERSHIP_ERROR(400, "사용자가 조직의 멤버가 아닙니다."),
    TOKEN_EXPIRATION_MISSING(400, "토큰 만료 시간이 누락되었습니다."),
    MISSING_REQUIRED_ATTRIBUTE(400, "사용자의 필수 속성이 누락되었습니다."),
    INVALID_ACCESS_TOKEN(400, "액세스 토큰이 만료되었거나 유효하지 않습니다."),
    EMPTY_ACCESS_TOKEN(400, "액세스 토큰이 비어 있습니다."),

    NOTICE_NOT_FOUND(404, "공지사항을 찾을 수 없습니다."),

    REPORT_NOT_FOUND(404, "보고서를 찾을 수 없습니다."),
}
