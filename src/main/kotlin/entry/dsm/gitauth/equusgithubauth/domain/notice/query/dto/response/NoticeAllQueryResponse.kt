package entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice

data class NoticeAllQueryResponse(
    val noticeId: Long,
    val title: String,
    val keyWord: List<String>,
    val isFocusRecruit: Boolean,
    val isImportant: Boolean,
) {
    companion object {
        fun from(notice: Notice): NoticeAllQueryResponse =
            NoticeAllQueryResponse(
                noticeId = notice.noticeId,
                title = notice.title,
                keyWord = notice.keyWord,
                isFocusRecruit = notice.isFocusRecruit,
                isImportant = notice.isImportant,
            )
    }
}
