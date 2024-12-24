package entry.dsm.gitauth.equusgithubauth.global.error.exception


class BusinessException(
    val errorCode: ErrorCode
): RuntimeException()