package entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.enums.ImportanceOfRecruit
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice

data class NoticeQueryResponse(
    val noticeId: Long,
    val title: String,
    val keyWord: String,
    val titleImageUrl: String,
    val description: String,
    val importanceOfRecruit: ImportanceOfRecruit
){
    companion object{
        fun from(notice: Notice) = NoticeQueryResponse(
            noticeId = notice.noticeId,
            title = notice.title,
            keyWord = notice.keyWord,
            titleImageUrl = notice.titleImageUrl,
            description = notice.description,
            importanceOfRecruit = notice.importanceOfRecruit
        )
    }
}