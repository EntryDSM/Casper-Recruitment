package entry.dsm.gitauth.equusgithubauth.global.exception

abstract class CustomException(
    private val errorCode: ErrorCode,
) : RuntimeException() {
    val statusCode: Int get() = errorCode.status
    override val message: String get() = errorCode.message
}