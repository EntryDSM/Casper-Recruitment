package entry.dsm.gitauth.equusgithubauth.domain.notice.query.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response.NoticeQueryResponse
import entry.dsm.gitauth.equusgithubauth.domain.notice.query.repository.NoticeQueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeQueryService(
    private val noticeQueryRepository: NoticeQueryRepository, // 수정된 부분
) {
    @Transactional(readOnly = true)
    fun getNotice(noticeId: Long): NoticeQueryResponse {
        val notice =
            noticeQueryRepository.findByIdOrNull(noticeId)
                ?: throw IllegalArgumentException("Notice id $noticeId not found")

        return NoticeQueryResponse.from(notice)
    }
}
