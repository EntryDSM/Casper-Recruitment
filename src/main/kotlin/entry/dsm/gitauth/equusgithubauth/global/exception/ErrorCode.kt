package entry.dsm.gitauth.equusgithubauth.global.exception

enum class ErrorCode(
    val status: Int,
    val message: String,
) {
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_INFO_FETCH_ERROR(500, "GitHub 사용자 정보를 가져올 수 없습니다."),
    AUTHORIZED_CLIENT_NOT_FOUND(400, "사용자에 대한 인증된 클라이언트를 찾을 수 없습니다."),
    ORGANIZATION_MEMBERSHIP_ERROR(400, "사용자가 조직의 멤버가 아닙니다."),
    TOKEN_EXPIRATION_MISSING(400, "토큰 만료 시간이 누락되었습니다."),
    MISSING_REQUIRED_ATTRIBUTE(400, "사용자의 필수 속성이 누락되었습니다."),
    INVALID_ACCESS_TOKEN(400, "액세스 토큰이 만료되었거나 유효하지 않습니다."),
    EMPTY_ACCESS_TOKEN(400, "액세스 토큰이 비어 있습니다."),

    NOTICE_NOT_FOUND(404, "공지사항을 찾을 수 없습니다."),

    REPORT_NOT_FOUND(404, "보고서를 찾을 수 없습니다."),

    JWT_TOKEN_EXPIRED(401, "JWT 토큰이 만료되었습니다."),
    JWT_TOKEN_INVALID(401, "JWT 토큰이 유효하지 않습니다."),
    INVALID_AUTHORIZATION_CODE(401, "인증 코드가 유효하지 않습니다."),
    GITHUB_OAUTH(401, "GitHub 토큰 발급 중 오류 발생"),

    REFRESH_TOKEN_NOT_FOUND(404, "리프레시 토큰을 찾을 수 없습니다"),

    NO_TOKEN_IN_HEADER(404, "헤더에서 토큰을 찾을 수 없습니다"),
    INVALID_TOKEN_FORMAT(401, " 잘못된 토큰 형식입니다."),

    EMAIL_DOMAIN_IN_NOT_ALLOWED_EXCEPTION(401, "허용되지 않은 이메일 도메인입니다."),

    NOT_FOUND_OAUTH_PROVIDER(404, "Oauth Provider을 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR(500, "서버에 에러가 발생하였습니다."),

    AUTHENTICATION_PRINCIPAL_MISMATCH(403, "인증 주체 불일치"),
    UNAUTHORIZED_ORG_ACCESS(403, "조직에 가입된 사용자만 접근 가능합니다."),
}
