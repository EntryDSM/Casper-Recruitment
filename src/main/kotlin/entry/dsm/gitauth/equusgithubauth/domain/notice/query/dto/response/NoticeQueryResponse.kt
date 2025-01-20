package entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit

data class NoticeQueryResponse(
    val noticeId: Long,
    val title: String,
    val keyWord: List<String>,
    val titleImageUrl: String,
    val description: List<String>,
    val importanceOfRecruit: ImportanceOfRecruit,
) {
    companion object {
        fun from(notice: Notice) =
            NoticeQueryResponse(
                noticeId = notice.noticeId,
                title = notice.title,
                keyWord = notice.keyWord,
                titleImageUrl = notice.titleImageUrl,
                description = notice.description,
                importanceOfRecruit = notice.importanceOfRecruit,
            )
    }
}
