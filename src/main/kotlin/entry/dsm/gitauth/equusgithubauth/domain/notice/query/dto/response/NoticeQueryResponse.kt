package entry.dsm.gitauth.equusgithubauth.domain.notice.query.dto.response

import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.DescriptionItem
import entry.dsm.gitauth.equusgithubauth.domain.notice.entity.Notice

data class NoticeQueryResponse(
    val noticeId: Long,
    val title: String,
    val keyWord: List<String>,
    val titleImageUrl: String,
    val description: List<DescriptionItem>,
    val isFocusRecruit: Boolean,
    val isImportant: Boolean,
) {
    companion object {
        fun from(notice: Notice) =
            NoticeQueryResponse(
                noticeId = notice.noticeId,
                title = notice.title,
                keyWord = notice.keyWord,
                titleImageUrl = notice.titleImageUrl,
                description =
                    notice.descriptions.map { // 엔티티 구조도 변경 필요
                        DescriptionItem(it.title, it.content)
                    },
                isFocusRecruit = notice.isFocusRecruit,
                isImportant = notice.isImportant,
            )
    }
}
