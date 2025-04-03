package entry.dsm.gitauth.equusgithubauth.domain.notice.presentation

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.CreateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.UpdateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.service.CreateNoticeService
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.service.DeleteNoticeService
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.service.UpdateNoticeService
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response.NoticeAllQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response.NoticeQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.service.GetAllNoticesService
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.service.NoticeQueryService
import entry.dsm.gitauth.equusgithubauth.global.security.annotation.OnlyAdmin
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class NoticeController(
    private val createNoticeService: CreateNoticeService,
    private val updateNoticeService: UpdateNoticeService,
    private val deleteNoticeService: DeleteNoticeService,
    private val getAllNoticesService: GetAllNoticesService,
    private val noticeQueryService: NoticeQueryService,
) {
    @PostMapping
    @OnlyAdmin
    @ResponseStatus(HttpStatus.CREATED)
    fun createNotice(
        @RequestBody @Valid command: CreateNoticeCommand,
    ) {
        createNoticeService.createNotice(command)
    } // conflict 이슈로 나중에 검증 추가 예정

    @PatchMapping("/{noticeId}")
    @OnlyAdmin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateNotice(
        @PathVariable noticeId: Long,
        @RequestBody command: UpdateNoticeCommand,
    ) {
        updateNoticeService.updateNotice(noticeId, command)
    }

    @DeleteMapping("/{noticeId}")
    @OnlyAdmin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNotice(
        @PathVariable noticeId: Long,
    ) {
        deleteNoticeService.deleteNotice(noticeId)
    }

    @GetMapping
    fun getAllNotices(): List<NoticeAllQueryResponse> {
        return getAllNoticesService.getAllNotices()
    }

    @GetMapping("/{noticeId}")
    fun getNoticeById(
        @PathVariable noticeId: Long,
    ): NoticeQueryResponse {
        return noticeQueryService.getNotice(noticeId)
    }
}
