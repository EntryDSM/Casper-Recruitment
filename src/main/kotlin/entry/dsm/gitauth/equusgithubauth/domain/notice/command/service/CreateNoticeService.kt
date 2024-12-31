package entry.dsm.gitauth.equusgithubauth.domain.notice.command.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.CreateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.repository.NoticeRepository
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateNoticeService(
    private val noticeRepository: NoticeRepository,
) {
    @Transactional
    fun createNotice(command: CreateNoticeCommand) {
        val notice =
            Notice(
                noticeId = command.noticeId,
                title = command.title,
                keyWord = command.keyWord,
                titleImageUrl = command.titleImageUrl,
                description = command.description,
                importanceOfRecruit = command.importanceOfRecruit,
                reports = listOf(),
            )
        noticeRepository.save(notice)
    }
}
