package entry.dsm.gitauth.equusgithubauth.domain.notice.command.service

import entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request.CreateNoticeCommand
import entry.dsm.gitauth.equusgithubauth.domain.notice.command.repository.NoticeRepository
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.NoticeDescription
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
                descriptions = mutableListOf(),
                isFocusRecruit = command.isFocusRecruit,
                isImportant = command.isImportant,
                reports = listOf(),
            )
        command.description.forEach { descDto ->
            notice.descriptions.add(
                NoticeDescription(
                    title = descDto.title,
                    content = descDto.content,
                    notice = notice,
                ),
            )
        }

        noticeRepository.save(notice)
    }
}
