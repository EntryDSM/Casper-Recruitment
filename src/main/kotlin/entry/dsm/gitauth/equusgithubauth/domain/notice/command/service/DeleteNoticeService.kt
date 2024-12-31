package entry.dsm.gitauth.equusgithubauth.domain.notice.command.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.repository.NoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteNoticeService(
    private val noticeRepository: NoticeRepository
) {

    @Transactional
    fun deleteNotice(noticeId: Long) {
        val notice = noticeRepository.findByIdOrNull(noticeId)
            ?: throw IllegalArgumentException("Notice with id $noticeId not found")

        noticeRepository.delete(notice) // 공지사항 삭제
    }
}
