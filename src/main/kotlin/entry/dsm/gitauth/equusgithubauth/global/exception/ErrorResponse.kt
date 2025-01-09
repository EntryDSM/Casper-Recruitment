package entry.dsm.gitauth.equusgithubauth.global.exception

import java.time.LocalDateTime

class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val timestamp: LocalDateTime,
    val path: String,
    val method: String,
)
