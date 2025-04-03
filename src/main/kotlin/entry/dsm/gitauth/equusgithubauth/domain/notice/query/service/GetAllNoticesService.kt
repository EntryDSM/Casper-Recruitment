package entry.dsm.gitauth.equusgithubauth.domain.notice.query.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response.NoticeAllQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.repository.NoticeQueryRepository
import org.springframework.stereotype.Service

@Service
class GetAllNoticesService(
    private val noticeQueryRepository: NoticeQueryRepository,
) {
    fun getAllNotices(): List<NoticeAllQueryResponse> = noticeQueryRepository.findAll().map(NoticeAllQueryResponse::from)
}
