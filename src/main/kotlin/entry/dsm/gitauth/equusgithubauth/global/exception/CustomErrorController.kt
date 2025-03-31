package entry.dsm.gitauth.equusgithubauth.global.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomErrorController : ErrorController {

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "알 수 없는 오류가 발생했습니다."
        private const val ERROR_TITLE = "서버 오류"
        private const val ERROR_ATTRIBUTE_STATUS = "jakarta.servlet.error.status_code"
        private const val ERROR_ATTRIBUTE_MESSAGE = "jakarta.servlet.error.message"
    }

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        val status =
            request.getAttribute(ERROR_ATTRIBUTE_STATUS) as? Int
                ?: HttpStatus.INTERNAL_SERVER_ERROR.value()
        val message =
            request.getAttribute(ERROR_ATTRIBUTE_MESSAGE)?.toString()
                ?: DEFAULT_ERROR_MESSAGE

        val errorResponse =
            mapOf(
                "error" to ERROR_TITLE,
                "message" to message,
                "status" to status,
            )

        return ResponseEntity(errorResponse, HttpStatus.valueOf(status))
    }
}