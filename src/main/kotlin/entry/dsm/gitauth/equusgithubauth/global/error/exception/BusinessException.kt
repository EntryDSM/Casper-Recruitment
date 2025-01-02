package entry.dsm.gitauth.equusgithubauth.global.error.exception


abstract class BusinessException(
    val errorCode: ErrorCode
): RuntimeException() {
    val statusCode: Int
        get() = errorCode.statusCode

    override val message: String
        get() = errorCode.message
}