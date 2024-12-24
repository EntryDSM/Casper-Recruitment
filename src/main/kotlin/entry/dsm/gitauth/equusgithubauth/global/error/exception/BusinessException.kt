package entry.dsm.gitauth.equusgithubauth.global.error.exception


abstract class BusinessException(
    val errorCode: ErrorCode
): RuntimeException()