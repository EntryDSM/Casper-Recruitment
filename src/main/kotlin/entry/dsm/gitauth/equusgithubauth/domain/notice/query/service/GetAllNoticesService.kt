package entry.dsm.gitauth.equusgithubauth.domain.notice.query.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.query.repository.NoticeQueryRepository
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response.NoticeQueryResponse
import org.springframework.stereotype.Service

@Service
class GetAllNoticesService(
    private val noticeQueryRepository: NoticeQueryRepository // 수정된 부분
) {
    fun getAllNotices(): List<NoticeQueryResponse> =
        noticeQueryRepository.findAll().map(NoticeQueryResponse::from) // 수정된 부분
}
