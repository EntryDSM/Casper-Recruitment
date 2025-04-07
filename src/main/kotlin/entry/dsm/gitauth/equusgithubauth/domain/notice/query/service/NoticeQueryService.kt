package entry.dsm.gitauth.equusgithubauth.domain.notice.query.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.exception.NoticeNotFoundException
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response.NoticeQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.repository.NoticeQueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeQueryService(
    private val noticeQueryRepository: NoticeQueryRepository,
) {
    @Transactional(readOnly = true)
    fun getNotice(noticeId: Long): NoticeQueryResponse {
        val notice =
            noticeQueryRepository.findByIdOrNull(noticeId)
                ?: throw NoticeNotFoundException()
        return NoticeQueryResponse.from(notice)
    }
}
