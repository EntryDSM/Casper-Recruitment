package entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit

data class CreateNoticeCommand(
    val noticeId: Long,
    val title: String,
    val keyWord: String,
    val titleImageUrl: String,
    val description: String,
    val importanceOfRecruit: ImportanceOfRecruit
)