package entry.dsm.gitauth.equusgithubauth.global.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomErrorController : ErrorController {
    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        val status =
            request.getAttribute("jakarta.servlet.error.status_code") as? Int
                ?: HttpStatus.INTERNAL_SERVER_ERROR.value()
        val message =
            request.getAttribute("jakarta.servlet.error.message")?.toString()
                ?: "알 수 없는 오류가 발생했습니다."

        val errorResponse =
            mapOf(
                "error" to "서버 오류",
                "message" to message,
                "status" to status,
            )

        return ResponseEntity(errorResponse, HttpStatus.valueOf(status))
    }
}
