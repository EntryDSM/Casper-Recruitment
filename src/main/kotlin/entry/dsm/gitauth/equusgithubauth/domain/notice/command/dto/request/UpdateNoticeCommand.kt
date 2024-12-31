package entry.dsm.gitauth.equusgithubauth.domain.notice.command.dto.request

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit

data class UpdateNoticeCommand(
    val title: String,
    val keyWord: String,
    val titleImageUrl: String,
    val description: String,
    val importanceOfRecruit: ImportanceOfRecruit
)
// conflict 이슈로 나중에 검증 추가 예정