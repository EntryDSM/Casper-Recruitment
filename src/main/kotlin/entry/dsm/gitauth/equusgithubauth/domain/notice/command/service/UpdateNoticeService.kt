package entry.dsm.gitauth.equusgithubauth.domain.notice.command.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.UpdateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.repository.NoticeRepository
import entry.dsm.gitauth.equusgithubauth.domain.notice.exception.NoticeNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateNoticeService(
    private val noticeRepository: NoticeRepository,
) {
    @Transactional
    fun updateNotice(
        noticeId: Long,
        command: UpdateNoticeCommand,
    ) {
        val notice =
            noticeRepository.findByIdOrNull(noticeId)
                ?: throw NoticeNotFoundException
        notice.update(command)
    }
}
